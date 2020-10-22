package com.example.demo.web.rest;

import com.example.demo.model.ShoppingCart;
import com.example.demo.service.AuthService;
import com.example.demo.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;


    public ShoppingCartRestController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @PostMapping
    public ShoppingCart createNewShoppingCart(){
        return this.shoppingCartService.createNewShoppingCart(this.authService.getCurrentUserId());
    }

    @GetMapping
    public List<ShoppingCart> findAllByUsername(){
        return this.shoppingCartService.findAllByUsername(this.authService.getCurrentUserId());
    }

    @PatchMapping("/cancel")
    public ShoppingCart cancelActiveShoppingCart(){
        return this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/{watchId}/watches")
    public ShoppingCart addNewWatchToShoppingCart(@PathVariable Long watchId){
        return this.shoppingCartService.addNewWatchToShoppingCart(this.authService.getCurrentUserId(), watchId);
    }

    @DeleteMapping("/{watchId}/watches")
    public ShoppingCart removeWatchFromShoppingCart(@PathVariable Long watchId){
        return this.shoppingCartService.removeWatchFromShoppingCart(this.authService.getCurrentUserId(), watchId);
    }

    @PatchMapping("/checkout")
    public ShoppingCart checkout(){
        return null;
    }
}
