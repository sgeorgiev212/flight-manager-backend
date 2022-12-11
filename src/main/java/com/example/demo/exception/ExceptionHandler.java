package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalAgrumentException(IllegalArgumentException exception) {
         return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
