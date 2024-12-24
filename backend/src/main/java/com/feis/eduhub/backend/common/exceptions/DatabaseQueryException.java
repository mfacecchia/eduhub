package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there is an error during any database query operation.
 * Returns HTTP status code 400 (Bad Request) when thrown.
 * 
 * @see AppException
 */
public class DatabaseQueryException extends AppException {
    private final static int STATUS_CODE = 400;

    public DatabaseQueryException(String message) {
        super(STATUS_CODE, message);
    }

    public DatabaseQueryException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
