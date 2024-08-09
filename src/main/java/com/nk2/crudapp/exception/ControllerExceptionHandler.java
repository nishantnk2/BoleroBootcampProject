package com.nk2.crudapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorMessage> badRequestException(BadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ErrorMessage> noContentException(InvalidRequestException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), HttpStatus.NO_CONTENT.value()), HttpStatus.NO_CONTENT);
    }
}
