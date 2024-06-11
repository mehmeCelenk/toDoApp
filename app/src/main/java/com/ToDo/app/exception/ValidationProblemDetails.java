package com.ToDo.app.exception;

import java.util.Map;

public class ValidationProblemDetails extends ProblemDetail{
    private Map<String, String> validationErrors;

    public ValidationProblemDetails(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationProblemDetails() {
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
