package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WatchNotFoundException extends RuntimeException {
    public WatchNotFoundException(Long id){
        super(String.format("Watch with %d id is not found", id));
    }
}
