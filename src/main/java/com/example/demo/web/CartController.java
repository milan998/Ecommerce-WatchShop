package com.example.demo.web;

import com.example.demo.model.ShoppingCart;
import com.example.demo.service.AuthService;
import com.example.demo.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;


    public CartController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @GetMapping
    public String getCartPage(Model model){
        ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }


    @PostMapping("/{watchId}/add-watch")
    public String addProductToShoppingCart(@PathVariable Long watchId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addNewWatchToShoppingCart(this.authService.getCurrentUserId(), watchId);
        } catch (RuntimeException ex) {
            return "redirect:/watches?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/cart";
    }


    @PostMapping("/{watchId}/remove-watch")
    public String removeProductToShoppingCart(@PathVariable Long watchId) {
        ShoppingCart shoppingCart = this.shoppingCartService.removeWatchFromShoppingCart(this.authService.getCurrentUserId(), watchId);
        return "redirect:/cart";
    }

}
