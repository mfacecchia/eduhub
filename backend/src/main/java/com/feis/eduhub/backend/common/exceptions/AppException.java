package com.feis.eduhub.backend.common.exceptions;

/**
 * Custom generic App Exception for application-specific errors handling.
 * This exception includes an HTTP status code along with the error message for
 * useful erorr logging and response generation.
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