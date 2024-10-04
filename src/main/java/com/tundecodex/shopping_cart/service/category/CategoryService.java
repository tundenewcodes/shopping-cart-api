package com.tundecodex.shopping_cart.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tundecodex.shopping_cart.exceptions.AlreadyExistException;
import com.tundecodex.shopping_cart.exceptions.CategoryNotFoundException;
import com.tundecodex.shopping_cart.model.Category;
import com.tundecodex.shopping_cart.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return Optional.ofNullable(categoryRepository.findByName(categoryName))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException(category.getName() + " already exist"));

    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(() -> new CategoryNotFoundException("Could not find category!"));
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(categoryRepository::delete, () -> {
            throw new CategoryNotFoundException("Category not found!");
        });
    }

}
