package com.currencies.exception;

import com.currencies.exception.exceptions.JsonParseException;
import com.currencies.exception.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseBody
    public ErrorResponse notFound(NotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    @ResponseBody
    public ErrorResponse jsonPareException(JsonParseException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message);
    }
}
