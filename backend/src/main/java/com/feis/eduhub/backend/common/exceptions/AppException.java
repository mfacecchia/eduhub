package com.feis.eduhub.backend.common.exceptions;

/**
 * Custom generic App Exception for application-specific errors handling.
 * This exception includes an HTTP status code along with an error message in a
 * user friendly format (e.g. the more generic the better) for
 * useful erorr logging and response generation.
 * 
 * It is also possible to pass a {@code Throwable} cause representing the
 * exception that thrown the custom App Exception.
 */
public class AppException extends Exception {
    private final int statusCode;

    public AppException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public AppException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}