package com.feis.eduhub.backend.common.middlewares;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.feis.eduhub.backend.features.auth.AlreadyAuthenticatedException;
import com.feis.eduhub.backend.features.auth.jwt.JsonWebToken;
import com.feis.eduhub.backend.features.auth.jwt.JwtData;
import com.feis.eduhub.backend.features.auth.jwt.JwtService;
import com.feis.eduhub.backend.features.auth.jwt.errors.TokenValidationException;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

/**
 * Middleware class for handling user authentication state checks.
 * This class provides functionality to verify if a user is logged in by
 * validating a JWT (JSON Web Token) stored in request's cookies.
 * In order for this o work, the class uses the {@link JwtService} class for
 * token management and validation operations.
 * 
 * @see JwtService
 * @see JsonWebToken
 * @see Handler
 * @see TokenValidationException
 * @see AlreadyAuthenticatedException
 */
public class IsLoggedInMiddleware {
    private static final JwtService jwtService = new JwtService();

    /**
     * Middleware handler for user authentication state check.
     * 
     * If the JWT is invalid, then the token gets removed from the user cookies and
     * persistence storage as well.
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
     * @param setAccountId             If true adds a {@code accountId} context
     *                                 attribute which can be used by next handlers
     *                                 as a reference of which account is making the
     *                                 request
     * @param setJwt                   If true adds a {@code jwtData} context Object
     *                                 attribute which represents the auth token
     *                                 that is getting used to authenticate the
     *                                 account
     * 
     * @return A Handler (used as a middleware) that processes the
     *         authentication verification logic.
     * @throws UnauthorizedResponse          If the token is missing (when strict is
     *                                       true) or invalid.
     * @throws AlreadyAuthenticatedException If sendResponseOnValidToken is true and
     *                                       a valid token is present.
     * 
     * @see Handler
     */
    public static Handler isLoggedIn(boolean strict, boolean sendResponseOnValidToken,
            boolean setAccountId, boolean setJwt) {
        return (ctx) -> {
            try {
                String token = ctx.cookie("sessionId");
                if (token == null && strict) {
                    throw new TokenValidationException("Missing or invalid token");
                }
                if (token != null) {
                    JwtData jwtData = JsonWebToken.validateToken(token);
                    String result = jwtService.getJwt(jwtData.getJti());
                    if (result == null) {
                        removeAuthCookie(ctx);
                        throw new TokenValidationException("Invalid token");
                    }
                    if (sendResponseOnValidToken) {
                        throw new AlreadyAuthenticatedException("Already logged in");
                    }
                    if (setAccountId) {
                        ctx.attribute("accountId", jwtData.getAccountId());
                    }
                    if (setJwt) {
                        ctx.attribute("jwtData", jwtData);
                    }
                }
            } catch (JWTVerificationException e) {
                removeAuthCookie(ctx);
                throw new TokenValidationException("Invalid token");
            }
        };
    }

    private static void removeAuthCookie(Context ctx) {
        ctx.removeCookie("sessionId");
    }
}
