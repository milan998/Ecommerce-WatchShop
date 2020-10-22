package com.example.demo.service.impl;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.model.Watch;
import com.example.demo.model.dto.ChargeRequest;
import com.example.demo.model.enumerations.CartStatus;
import com.example.demo.model.exceptions.*;
import com.example.demo.repository.ShoppingCartRepository;
//import com.example.demo.service.PaymentService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final WatchService watchService;
    private final PaymentService paymentService;

    public ShoppingCartServiceImpl(UserService userService, ShoppingCartRepository shoppingCartRepository, WatchService watchService, PaymentService paymentService) {
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.watchService = watchService;
        this.paymentService = paymentService;
    }


    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(user.getUsername(), CartStatus.CREATED)){
            throw new ShoppingCartIsAlreadyActive(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED).orElseGet(() -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            User user = this.userService.findById(userId);
            shoppingCart.setUser(user);
            return this.shoppingCartRepository.save(shoppingCart);
        });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        shoppingCart.setCanceledDate(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    @Transactional
    public ShoppingCart addNewWatchToShoppingCart(String userId, Long watchId) {
        Watch watch = this.watchService.findById(watchId);
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        for(Watch w : shoppingCart.getWatches()){
            if(w.getId().equals(watchId)){
                throw new WatchIsAlreadyInShoppingCart(watchId);
            }
        }
        shoppingCart.getWatches().add(watch);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeWatchFromShoppingCart(String userId, Long watchId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setWatches(
                shoppingCart.getWatches()
                .stream()
                .filter(watch -> !watch.getId().equals(watchId))
                .collect(Collectors.toList()));
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkout(String userId, ChargeRequest chargeRequest){
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsAlreadyActive(userId));

        float price = 0;

        for(Watch watch : shoppingCart.getWatches()){
            if(watch.getQuantity() == 0){
                throw new WatchIsOutOfStockException(watch.getName());
            }else
                watch.setQuantity(watch.getQuantity() - 1);
                price+=watch.getPrice();
        }

        Charge charge = null;
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }

        shoppingCart.setWatches(shoppingCart.getWatches());
        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setCanceledDate(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
