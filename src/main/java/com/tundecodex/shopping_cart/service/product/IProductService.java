package com.tundecodex.shopping_cart.service.product;

import java.util.List;

import com.tundecodex.shopping_cart.dto.ProductDto;
import com.tundecodex.shopping_cart.model.Product;
import com.tundecodex.shopping_cart.request.AddProductRequest;
import com.tundecodex.shopping_cart.request.ProductUpdateRequest;

public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> getProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndCategory(String brand, String category);

    List<Product> getProductByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);

}
