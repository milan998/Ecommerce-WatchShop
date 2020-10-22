package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WatchIsAlreadyInShoppingCart extends RuntimeException {
    public WatchIsAlreadyInShoppingCart(Long watchId){
        super(String.format("Watch with %d id is already in cart", watchId));
    }
}
