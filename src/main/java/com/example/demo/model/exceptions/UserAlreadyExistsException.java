package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username){
        super(String.format("User with %s username already exists", username));
    }
}
