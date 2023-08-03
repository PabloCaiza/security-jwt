package com.quesito.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.quesito.security.exceptions.ErrorResponse;
import com.quesito.security.exceptions.CustomerAlreadyRegister;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(CustomerAlreadyRegister.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }
}
