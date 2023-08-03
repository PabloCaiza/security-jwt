package com.quesito.security.exceptions;

public class CustomerAlreadyRegister extends RuntimeException {
    public CustomerAlreadyRegister(String message) {
        super(message);
    }
}
