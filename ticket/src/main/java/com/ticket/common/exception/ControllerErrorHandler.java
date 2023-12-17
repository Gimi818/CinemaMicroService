package com.ticket.common.exception;

import com.ticket.common.exception.exceptions.TooLateToBookException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ControllerErrorHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TooLateToBookException.class)
    @ResponseBody
    public ErrorResponse tooLateToBookException(TooLateToBookException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message, HttpStatus.CONFLICT);
    }}
