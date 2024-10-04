package com.tundecodex.shopping_cart.repository;

import java.util.List;
import com.tundecodex.shopping_cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByProductId(Long productId);
}
