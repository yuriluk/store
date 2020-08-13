package org.example.store.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class IllegalRequestException extends RuntimeException {

    private final List<FieldError> errors;

    public IllegalRequestException(List<FieldError> errors) {
        super();
        this.errors = errors;
    }

    public IllegalRequestException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

}