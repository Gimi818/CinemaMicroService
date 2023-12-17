package com.ticket.common.exception.exceptions;

public class TooLateToBookException extends  RuntimeException {

    public TooLateToBookException(final String message) {
        super(message);
    }

}
