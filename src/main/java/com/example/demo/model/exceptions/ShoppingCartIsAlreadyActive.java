package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ShoppingCartIsAlreadyActive extends RuntimeException {
    public ShoppingCartIsAlreadyActive(String userId){
        super(String.format("Shopping cart is already created for User: %s", userId));
    }
}
