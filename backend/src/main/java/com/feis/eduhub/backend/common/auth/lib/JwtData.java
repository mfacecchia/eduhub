package com.feis.eduhub.backend.common.auth.lib;

import java.time.Duration;
import java.time.Instant;

/**
 * A data class representing the most useful JWT information
 * such as the JWT token itself, its unique identifier (JTI),
 * and expiration time claims (represented in JWT's EPOCH Seconds time unit).
 */
public class JwtData {
    private final String token;
    private final String jti;
    private final long exp;

    /**
     * Builds the JWT Data Object.
     * 
     * @param token the JWT itself
     * @param jti   the token ID
     * @param exp   the expiry time in EPOCH seconds (if set to a negative value
     *              then no expiry is set to the token)
     * 
     * @throws IllegalArgumentException if the JWT or the JTI are blank
     */
    public JwtData(String token, String jti, long exp) {
        if (token.isBlank() || jti.isBlank()) {
            throw new IllegalArgumentException("Token or jti empty");
        }
        this.token = token;
        this.jti = jti;
        this.exp = exp;
    }

    /**
     * Calculates the total token TTL in seconds
     * 
     * @return the operation result in seconds
     *         as an {@code int} value
     * 
     * @apiNote since this method returns the duration as an integer, there could
     *          be chances that the result will run into data loss or unexpected
     *          behavior if the result exceeds int max possible value
     *          ({@code int} data can, by intentional design, store roughly a 68
     *          years period time in seconds unit)
     */
    public int getExpIntDuration() {
        Duration duration = Duration.between(Instant.EPOCH, Instant.ofEpochSecond(exp));
        return Long.valueOf(duration.getSeconds()).intValue();
    }

    /**
     * Calculates the total token TTL in seconds
     * 
     * @return the operation result in seconds
     *         as a {@code long} value
     */
    public long getExpLongDuration() {
        Duration duration = Duration.between(Instant.EPOCH, Instant.ofEpochSecond(exp));
        return duration.getSeconds();
    }

    public String getToken() {
        return token;
    }

    public String getJti() {
        return jti;
    }

    public long getExp() {
        return exp;
    }
}
