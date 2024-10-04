package com.tundecodex.shopping_cart.service.category;

import java.util.List;

import com.tundecodex.shopping_cart.model.Category;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);

    Category getCategoryByName(String categoryName);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategoryById(Long categoryId);

}
