package com.user.user.exception.exceptions;

public class PasswordConflictException extends RuntimeException {
    public PasswordConflictException(final String message) {
        super(message);
    }
}
