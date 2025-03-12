package org.example.backend.Handler;

import org.example.backend.DTOs.CustomErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    //TODO implement stuff

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorMessage MovieNotFoundException(NoSuchElementException exception) {
        return new CustomErrorMessage(exception.getMessage(), Instant.now());
    }
}
