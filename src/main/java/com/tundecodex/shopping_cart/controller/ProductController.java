package com.tundecodex.shopping_cart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tundecodex.shopping_cart.dto.ProductDto;
import com.tundecodex.shopping_cart.exceptions.ProductNotFoundException;
import com.tundecodex.shopping_cart.exceptions.ResourceNotFoundException;
import com.tundecodex.shopping_cart.model.Category;
import com.tundecodex.shopping_cart.model.Product;
import com.tundecodex.shopping_cart.request.AddProductRequest;
import com.tundecodex.shopping_cart.request.ProductUpdateRequest;
import com.tundecodex.shopping_cart.response.ApiResponse;
import com.tundecodex.shopping_cart.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {

        try {
            List<Product> product = productService.getProducts();
            List <ProductDto> convertedProducts = productService.getConvertedProducts(product);
            return ResponseEntity.ok(new ApiResponse(true, "product fetched successfully", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("getting products failed!", e.getMessage()));

        }
    }

    @GetMapping("/product-by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse(true, "product fetched successfully", productDto));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("getting products failed!", e.getMessage()));

        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok(new ApiResponse(true, "new product added!", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("creating products failed", e.getMessage()));
        }

    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,
            @PathVariable Long productId) {

        try {
            Product product = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse(true, "product updated successfully", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("product not found", e.getMessage()));
        }

    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {

        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse(true, "product updated successfully", productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product-by-name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductByName(name);
            List <ProductDto> convertedProducts = productService.getConvertedProducts(products);


            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
            }

            return ResponseEntity.ok(new ApiResponse(true, "product by name fetched!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product-by-brand/")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List <ProductDto> convertedProducts = productService.getConvertedProducts(products);


            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
            }

            return ResponseEntity.ok(new ApiResponse(true, "product by Brand fetched!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product-by-category/")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            List <ProductDto> convertedProducts = productService.getConvertedProducts(products);


            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
            }

            return ResponseEntity.ok(new ApiResponse(true, "product by category fetched!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product-by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            List <ProductDto> convertedProducts = productService.getConvertedProducts(products);

            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
            }

            return ResponseEntity.ok(new ApiResponse(true, "product by name  and brand fetched!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product-by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,
            @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            List <ProductDto> convertedProducts = productService.getConvertedProducts(products);


            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
            }

            return ResponseEntity
                    .ok(new ApiResponse(true, "product by category and brand  and brand fetched!", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



    
    @GetMapping("/count-by-category-and-brand")
    public ResponseEntity<ApiResponse> countProductByCategoryAndBrand(@RequestParam String category,
            @RequestParam String brand) {
        try {
           var products = productService.countProductsByBrandAndName(category, brand);


            return ResponseEntity
                    .ok(new ApiResponse(true, "product counted!", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
