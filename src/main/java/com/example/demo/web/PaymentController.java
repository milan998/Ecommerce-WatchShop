package com.example.demo.web;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.Watch;
import com.example.demo.model.dto.ChargeRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.ShoppingCartService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {


    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;


    public PaymentController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping("/buy")
    public String getCheckoutPage(Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "usd");
            model.addAttribute("amount", (int) (shoppingCart.getWatches().stream().mapToDouble(Watch::getPrice).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        }catch (RuntimeException ex){
            return "redirect:/watches?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/buy")
    public String checkoutPage(ChargeRequest chargeRequest, Model model){
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.checkout(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/watches?message=Successful Payment";
        } catch (RuntimeException ex) {
            return "redirect:/payments/buy?error=" + ex.getLocalizedMessage();
        }

    }

}
