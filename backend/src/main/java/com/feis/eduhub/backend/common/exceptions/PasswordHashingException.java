package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when string hashing fails.
 * Returns HTTP status code 400 (Bad Request) when thrown.
 * 
 * @see AppException
 */
public class PasswordHashingException extends AppException {
    private final static int STATUS_CODE = 400;

    public PasswordHashingException(String message) {
        super(STATUS_CODE, message);
    }

    public PasswordHashingException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
