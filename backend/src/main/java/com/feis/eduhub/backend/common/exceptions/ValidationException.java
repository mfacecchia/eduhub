package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when the values passed in the request appear to be invalid
 * after a validation process.
 * Returns HTTP status code 400 (Bad Request) when thrown.
 * 
 * @see AppException
 */
public class ValidationException extends AppException {
    private final static int STATUS_CODE = 400;

    public ValidationException(String message) {
        super(STATUS_CODE, message);
    }

    public ValidationException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
