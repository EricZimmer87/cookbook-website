package com.cookbookwebsite.service;

import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.repository.CategoryRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import com.cookbookwebsite.dto.category.CategoryDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories
    @Override
    @Transactional
    public List<CategoryDTO> getAllCategoryDTOs() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(c -> new CategoryDTO(c, c.getRecipes() != null ? c.getRecipes().size() : 0))
                .toList();
    }


    // Get categoryDTO by categoryID
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return new CategoryDTO(category, category.getRecipes() != null ? category.getRecipes().size() : 0);
    }


    // Get category entity by category ID
    @Override
    @Transactional(readOnly = true)
    public Category getCategoryEntityById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // Update category
    @Override
    @Transactional
    public Category saveCategory(Category category) {
        Category saved = categoryRepository.save(category);
        Hibernate.initialize(saved.getRecipes());
        return saved;
    }

    // Create category
    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Delete category
    @Override
    @Transactional
    public void deleteCategoryById(Integer categoryId) { categoryRepository.deleteById(categoryId); }
}
