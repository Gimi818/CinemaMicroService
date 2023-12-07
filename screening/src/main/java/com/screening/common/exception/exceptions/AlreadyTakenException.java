package com.screening.common.exception.exceptions;

public class AlreadyTakenException extends RuntimeException{
    public AlreadyTakenException(final String message) {
        super(message);
    }
}
