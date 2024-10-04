package com.tundecodex.shopping_cart.request;

import java.math.BigDecimal;
import java.util.List;

import com.tundecodex.shopping_cart.model.Category;
import com.tundecodex.shopping_cart.model.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<Image> images;
    
}
