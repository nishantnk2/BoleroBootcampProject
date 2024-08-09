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
public class ControllerExceptionHandler <T extends Exception> {

    private final Map<Class<T>, HttpStatus> map = new HashMap<>();

    public ControllerExceptionHandler(){
       map.put((Class<T>) NoSuchElementException.class, HttpStatus.NOT_FOUND);
       map.put((Class<T>) BadRequestException.class, HttpStatus.BAD_REQUEST);
       map.put((Class<T>) Exception.class, HttpStatus.INTERNAL_SERVER_ERROR);
       map.put((Class<T>) NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
       map.put((Class<T>) IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
       map.put((Class<T>) NoResourceFoundException.class, HttpStatus.NOT_FOUND);
       map.put((Class<T>) CustomException.class, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handle(Exception ex){
        HttpStatus responseCode = map.get(ex.getClass());
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), responseCode.value()), responseCode);
    }
}
