package com.service.film.exception.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
    public NotFoundException(final Long id) {
        super(String.format("No entity with UUID: %s", id));
    }

    public NotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
