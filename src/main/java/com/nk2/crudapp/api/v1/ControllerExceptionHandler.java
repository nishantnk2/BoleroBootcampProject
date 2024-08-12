package com.nk2.crudapp.api.v1;

import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.exception.ErrorMessage;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> map = new HashMap<>();

    static {
        map.put(NoSuchElementException.class, HttpStatus.NOT_FOUND);
        map.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
        map.put(Exception.class, HttpStatus.INTERNAL_SERVER_ERROR);
        map.put(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        map.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        map.put(NoResourceFoundException.class, HttpStatus.NOT_FOUND);
        map.put(CustomException.class, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handle(Exception ex) {
        HttpStatus responseCode = map.get(ex.getClass());
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), responseCode.value()), responseCode);
    }
}
