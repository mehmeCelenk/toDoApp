package com.ToDo.app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleGeneralNotFoundException(NotFoundException exception) {
        return createProblemDetails(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setMessage("VALIDATION.EXCEPTION");
        validationProblemDetails.setValidationErrors(new HashMap<>());

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                validationProblemDetails.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage()));

        return validationProblemDetails;
    }

    private ProblemDetail createProblemDetails(String message) {
        ProblemDetail problemDetails = new ProblemDetail();
        problemDetails.setMessage(message);
        return problemDetails;
    }
}
