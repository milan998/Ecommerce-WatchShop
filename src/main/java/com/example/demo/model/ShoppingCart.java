package com.example.demo.model;

import com.example.demo.model.enumerations.CartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shoppingCarts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.CREATED;

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime canceledDate;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Watch> watches = new ArrayList<>();

    public ShoppingCart() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(LocalDateTime canceledDate) {
        this.canceledDate = canceledDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Watch> getWatches() {
        return watches;
    }

    public void setWatches(List<Watch> watches) {
        this.watches = watches;
    }
}
