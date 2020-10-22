package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class WatchIsOutOfStockException extends RuntimeException {
    public WatchIsOutOfStockException(String name){
        super(String.format("%s is out of stock", name));
    }
}
