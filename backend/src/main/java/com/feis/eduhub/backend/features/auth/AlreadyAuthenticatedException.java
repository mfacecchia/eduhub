package com.feis.eduhub.backend.features.auth;

import com.feis.eduhub.backend.common.exceptions.AppException;

/**
 * Exception thrown when an already authenticated user attempts to authenticate
 * again.
 * Extends the base {@link AppException} with a specific HTTP status
 * code of 200 (OK).
 */
public class AlreadyAuthenticatedException extends AppException {
    private final static int STATUS_CODE = 200;

    public AlreadyAuthenticatedException(String message) {
        super(STATUS_CODE, message);
    }

    public AlreadyAuthenticatedException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
