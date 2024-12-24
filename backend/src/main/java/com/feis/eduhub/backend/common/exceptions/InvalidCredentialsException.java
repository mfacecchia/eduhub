package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when user credentials are invalid.
 * Returns HTTP status code 401 (Unathorized) when thrown.
 * 
 * @see AppException
 */
public class InvalidCredentialsException extends AppException {
    private final static int STATUS_CODE = 401;

    public InvalidCredentialsException(String message) {
        super(STATUS_CODE, message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
