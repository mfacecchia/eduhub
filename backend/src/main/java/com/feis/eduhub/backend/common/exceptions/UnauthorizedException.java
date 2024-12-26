package com.feis.eduhub.backend.common.exceptions;

public class UnauthorizedException extends AppException {
    private final static int STATUS_CODE = 403;

    public UnauthorizedException(String message) {
        super(STATUS_CODE, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
