package com.feis.eduhub.backend.common.auth.lib;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.feis.eduhub.backend.common.lib.Environment;

/**
 * Utility class to sign and verify JWTs through HMAC256 algorithm. The default
 * expiration time is 6 month (can be completely disabled based on user
 * choice e.g. when clicking on a "keep me signed in" button), after that
 * time the token will be no longer considered valid. Includes a JTI claim (in
 * form of UUIDv4) to store the token in a Redis storage and implement tokens
 * whitelisting and device logout functionalities, and a `acc_id` claim used to
 * keep track of the user making the request.
 * 
 * @see com.auth0.jwt.JWT
 * @see com.auth0.jwt.algorithms.Algorithm
 */
public class JsonWebToken {
    private final static Map<String, String> configValues = Environment
            .getEnvironmentVariables(Arrays.asList("JWT_SECRET"));
    // NOTE: 1 months
    private static final long DEFAULT_EXPIRATION_TIME = 60 * 1000 * 60 * 24 * 30 * 6;
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(configValues.get("JWT_SECRET"));
    private static final JWTVerifier verifier = JWT.require(ALGORITHM).build();

    /**
     * Generates a JWT using HMAC256 as token signing algorithm.
     * 
     * @param accountId     the account_id making the request (possibly obtained
     *                      from the database)
     * @param setExpiration se this to {@code true} if you want to disable the token
     *                      expiration and make it last "forever" (not recommended),
     *                      otherwise {@code false}
     * @return an {@link com.feis.eduhub.backend.common.auth.lib.JwtData JWTData}
     *         Object representing the most useful token information.
     * @throws JWTCreationException if the claims could not be converted to a valid
     *                              JSON or there was a problem with the signing
     *                              key.
     * 
     * @see Builder
     * @see JwtData
     */
    public static JwtData generateToken(long accountId, boolean setExpiration)
            throws JWTCreationException {

        String jti = UUID.randomUUID().toString();
        Instant exp = null;

        // FIXME: Wrong expiration being set if `setExpiration` is false
        Builder jwtBuilder = JWT.create()
                .withClaim("acc_id", accountId)
                .withIssuedAt(new Date())
                .withJWTId(jti);
        if (setExpiration) {
            exp = Instant.now().plusSeconds(DEFAULT_EXPIRATION_TIME);
            jwtBuilder.withExpiresAt(exp);
        }
        String token = jwtBuilder.sign(ALGORITHM);
        return new JwtData(
                token,
                accountId,
                jti,
                exp != null ? exp.getEpochSecond() : -1);
    }

    /**
     * Decodes a JWT using HMAC256 as token verification algorithm.
     * 
     * @param token the token to verificate
     * @return the {@code JTI} claim (if the verification succeeds)
     * @throws JWTVerificationException if any of the verification steps fail
     */
    public static String validateToken(String token) throws JWTVerificationException {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getId();
    }
}
