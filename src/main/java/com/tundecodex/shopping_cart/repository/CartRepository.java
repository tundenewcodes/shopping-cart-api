package com.tundecodex.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tundecodex.shopping_cart.model.Cart;

public interface CartRepository  extends JpaRepository<Cart, Long> {
   // Cart findByUserId(Long userId);
}
