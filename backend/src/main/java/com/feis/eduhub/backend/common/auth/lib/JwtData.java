package com.feis.eduhub.backend.common.auth.lib;

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
