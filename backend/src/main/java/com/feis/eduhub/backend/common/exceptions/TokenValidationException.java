package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when token validation fails (e.g. expired token)
 * Returns HTTP status code 401 (Unauthorized) when thrown.
 * 
 * @see AppException
 */
public class TokenValidationException extends AppException {
    private final static int STATUS_CODE = 401;

    public TokenValidationException(String message) {
        super(STATUS_CODE, message);
    }

    public TokenValidationException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
