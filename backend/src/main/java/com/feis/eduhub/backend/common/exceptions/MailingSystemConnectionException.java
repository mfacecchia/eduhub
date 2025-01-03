package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there are issues while trying to connect to the mailing
 * system.
 * 
 * @see AppException
 */
public class MailingSystemConnectionException extends AppException {
    private final static int STATUS_CODE = 500;

    public MailingSystemConnectionException(String message) {
        super(STATUS_CODE, message);
    }

    public MailingSystemConnectionException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
