package com.tundecodex.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tundecodex.shopping_cart.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUserId(Long userId);

    
}
