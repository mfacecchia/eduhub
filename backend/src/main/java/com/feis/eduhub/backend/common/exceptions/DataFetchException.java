package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there is an error while fetching data from the database
 * Returns HTTP status code 400 (Bad Request) when thrown.
 * 
 * @see AppException
 */
public class DataFetchException extends AppException {
    private final static int STATUS_CODE = 400;

    public DataFetchException(String message) {
        super(STATUS_CODE, message);
    }

    public DataFetchException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
