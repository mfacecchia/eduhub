package com.feis.eduhub.backend.common.exceptions;

/**
 * Exception thrown when there is a failure while sending an Email.
 * 
 * @see AppException
 */
public class EmailSendingException extends AppException {
    private final static int STATUS_CODE = 400;

    public EmailSendingException(String message) {
        super(STATUS_CODE, message);
    }

    public EmailSendingException(String message, Throwable cause) {
        super(STATUS_CODE, message, cause);
    }
}
