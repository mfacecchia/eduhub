package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there are issues connecting to the database.
 * Returns HTTP status code 500 (Internal Server Error) when thrown.
 */
public class DatabaseConnectionException extends AppException {
    private final static int STATUS_CODE = 500;

    public DatabaseConnectionException(String message) {
        super(STATUS_CODE, message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
