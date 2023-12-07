package com.screening.exception;

import com.screening.exception.exceptions.NotFoundException;
import com.screening.exception.exceptions.TimeDifferenceException;
import com.screening.exception.exceptions.TooLateException;
import com.screening.exception.exceptions.TooManyScreeningException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse notFound(NotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TooLateException.class)
    @ResponseBody
    public ErrorResponse tooLateException(TooLateException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TooManyScreeningException.class)
    @ResponseBody
    public ErrorResponse tooManyScreeningException(TooManyScreeningException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message, HttpStatus.CONFLICT);
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TimeDifferenceException.class)
    @ResponseBody
    public ErrorResponse timeDifferenceException(TimeDifferenceException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                ErrorResponse(message, HttpStatus.CONFLICT);
    }

}
