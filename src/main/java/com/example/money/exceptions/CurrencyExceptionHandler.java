package com.example.money.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CurrencyExceptionHandler {
    @ExceptionHandler(value = {CurrencyNotFoundException.class})
    public ResponseEntity<Object> handleCarException(RuntimeException exc){
        if(exc instanceof CurrencyNotFoundException){
            CurrencyExceptionResponse exception = new CurrencyExceptionResponse(exc.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        CurrencyExceptionResponse exception = new CurrencyExceptionResponse(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
