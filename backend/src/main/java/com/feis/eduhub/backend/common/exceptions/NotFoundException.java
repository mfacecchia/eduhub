package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when no data was found while fetching from a data soruce
 * (such as DB or Redis)
 * Returns HTTP status code 404 (Not Found) when thrown.
 * 
 * @see AppException
 */
public class NotFoundException extends AppException {
    private final static int STATUS_CODE = 404;

    public NotFoundException(String message) {
        super(STATUS_CODE, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
