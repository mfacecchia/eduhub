package com.feis.eduhub.backend.features.auth.jwt.errors;

import com.feis.eduhub.backend.common.exceptions.AppException;

/**
 * Exception thrown when auth token generation fails.
 * Returns HTTP status code 400 (Bad Request) when thrown.
 * 
 * @see AppException
 */
public class TokenGenerationException extends AppException {
    private final static int STATUS_CODE = 400;

    public TokenGenerationException(String message) {
        super(STATUS_CODE, message);
    }

    public TokenGenerationException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
