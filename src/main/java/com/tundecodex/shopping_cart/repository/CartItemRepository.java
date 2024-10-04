package com.tundecodex.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tundecodex.shopping_cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
