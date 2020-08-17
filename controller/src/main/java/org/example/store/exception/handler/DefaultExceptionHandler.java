package org.example.store.exception.handler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.store.exception.IllegalRequestException;
import org.example.store.service.exception.ResourceNotFoundException;
import org.example.store.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger logger = LogManager.getLogger(DefaultExceptionHandler.class);

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, BindException.class,
            UnsatisfiedServletRequestParameterException.class, IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> handleJsonMappingException(Exception e) {
        /*
         * Exception occurs when passed id is null. Status 400.
         */
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage("Request parameters are not valid!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFound(ResourceNotFoundException e) {
        /*
         * Exception occurs when passed id is null. Status 404.
         */
        logger.error(e.getMessage());
        String message = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(new ErrorMessage("Resource not found! " + message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> handleServiceException(ServiceException e) {
        /* Handles service exception. Status code 400. */
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<List<String>> handleValidation(IllegalRequestException e) {
        /*
         * Validation exceptions handling. Status code 400.
         */
        List<String> errors = new ArrayList<>();
        e.getErrors().forEach(er ->
                errors.add(String.format("Incorrect value for field %s : '%s'. %s.",
                        er.getField(), er.getRejectedValue(), er.getDefaultMessage())));
        if (e.getMessage() != null) {
            errors.add(0, e.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        /* Handles all other exceptions. Status code 500. */
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage("Internal server error."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
