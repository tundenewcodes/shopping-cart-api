package com.tundecodex.shopping_cart.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tundecodex.shopping_cart.dto.ImageDto;
import com.tundecodex.shopping_cart.dto.ProductDto;
import com.tundecodex.shopping_cart.exceptions.ProductNotFoundException;
import com.tundecodex.shopping_cart.model.Category;
import com.tundecodex.shopping_cart.model.Image;
import com.tundecodex.shopping_cart.model.Product;
import com.tundecodex.shopping_cart.repository.CategoryRepository;
import com.tundecodex.shopping_cart.repository.ImageRepository;
import com.tundecodex.shopping_cart.repository.ProductRepository;
import com.tundecodex.shopping_cart.request.AddProductRequest;
import com.tundecodex.shopping_cart.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {

        // check if the category already exists in the db if yes set it as the new
        // category else if no, then save it as the new category;
        // after saving set as the new category;

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);

                });

        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getDescription(), request.getPrice(), request.getBrand(), request.getInventory(), category);
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct -> updateProductExistingProduct(existingProduct, request))
                .map(productRepository::save).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateProductExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                productRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product not found!");
                });
    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String category) {

        return productRepository.findByBrandAndCategoryName(brand, category);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> product) {

        return product.stream().map(this::convertToDto).toList();

    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findImagesByProductId(productDto.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }





//     @Override
// public List<ProductDto> agetConvertedProducts(List<Product> products) {
//     return products.stream()
//                    .map(this::convertToDto)
//                    .collect(Collectors.toList());
// }



// @Override
// public ProductDto aconvertToDto(Product product) {
//     ProductDto productDto = modelMapper.map(product, ProductDto.class);
    
//     // Assuming imageRepository returns a list of images for a product
//     List<Image> images = imageRepository.findImagesByProductId(productDto.getId()); 
//     List<ImageDto> imageDtos = images.stream()
//                                      .map(image -> modelMapper.map(image, ImageDto.class))
//                                      .collect(Collectors.toList());
//     productDto.setImages(imageDtos);
    
//     return productDto;
// }


}
