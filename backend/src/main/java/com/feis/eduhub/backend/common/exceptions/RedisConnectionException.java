package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there are issues while connecting to Redis.
 * Returns HTTP status code 500 (Internal Server Error) when thrown.
 * 
 * @see AppException
 */
public class RedisConnectionException extends AppException {
    private final static int STATUS_CODE = 500;

    public RedisConnectionException(String message) {
        super(STATUS_CODE, message);
    }

    public RedisConnectionException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}