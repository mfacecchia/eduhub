package com.feis.eduhub.backend.features.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.feis.eduhub.backend.features.auth.jwt.JsonWebToken;
import com.feis.eduhub.backend.features.auth.jwt.JwtData;
import com.feis.eduhub.backend.features.auth.jwt.JwtService;
import com.feis.eduhub.backend.features.auth.jwt.errors.TokenValidationException;

import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

public class AuthMiddleware {
    private final JwtService jwtService = new JwtService();

    /**
     * Middleware handler for user authentication state check.
     * 
     * @param strict                   If true, throws
     *                                 {@link com.feis.eduhub.backend.features.auth.jwt.errors.TokenValidationException
     *                                 TokenValidationException} when no
     *                                 token is present in the header as a cookie
     *                                 named {@code sessionId}.
     *                                 If false, allows requests without tokens to
     *                                 pass through.
     * @param sendResponseOnValidToken If true, throws AlreadyAuthenticatedException
     *                                 when a valid token is present.
     *                                 Useful for endpoints that should only be
     *                                 accessed by non-authenticated users.
     * @param setAccountId             If true, sets the accountId attribute in the
     *                                 {@code context} for further operations with
     *                                 account's information.
     * @return A Handler (used as a middleware) that processes the
     *         authentication verification logic.
     * @throws UnauthorizedResponse          If the token is missing (when strict is
     *                                       true) or invalid.
     * @throws AlreadyAuthenticatedException If sendResponseOnValidToken is true and
     *                                       a valid token is present.
     * 
     * @see Handler
     */
    public Handler isLoggedIn(boolean strict, boolean sendResponseOnValidToken, boolean setAccountId) {
        return (ctx) -> {
            try {
                String token = ctx.cookie("sessionId");
                if (token == null && strict) {
                    throw new TokenValidationException("Missing or invalid token");
                }
                if (token != null) {
                    JwtData jwtData = JsonWebToken.validateToken(token);
                    String result = jwtService.getJwt(jwtData.getJti());
                    if ("nil".equals(result)) {
                        throw new TokenValidationException("Invalid token");
                    }
                    if (sendResponseOnValidToken) {
                        throw new AlreadyAuthenticatedException("Already logged in");
                    }
                    if (setAccountId) {
                        ctx.attribute("accountId", jwtData.getAccountId());
                    }
                }
            } catch (JWTVerificationException e) {
                throw new TokenValidationException("Invalid token");
            }
        };
    }
}
