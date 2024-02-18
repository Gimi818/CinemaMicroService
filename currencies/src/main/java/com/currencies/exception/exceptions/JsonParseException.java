package com.currencies.exception.exceptions;

public class JsonParseException extends RuntimeException {
    public JsonParseException(final String message, Throwable cause) {
        super(message, cause);
    }
}
