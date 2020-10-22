package com.example.demo.service;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.Watch;
import com.example.demo.model.dto.ChargeRequest;
import com.stripe.exception.StripeException;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart createNewShoppingCart(String userId);
    ShoppingCart getActiveShoppingCart(String userId); // ova vo cart service impl da e vratime aktivnata
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart findActiveShoppingCartByUsername(String userId); // ova go koristime vo payment controller da proverime dadena karticka
    List<ShoppingCart> findAllByUsername(String userId);
    ShoppingCart addNewWatchToShoppingCart(String userId, Long watchId);
    ShoppingCart removeWatchFromShoppingCart(String userId, Long watchId);
    ShoppingCart checkout(String userId, ChargeRequest chargeRequest);
}
