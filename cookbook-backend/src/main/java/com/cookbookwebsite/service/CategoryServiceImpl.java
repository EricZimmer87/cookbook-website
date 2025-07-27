package com.cookbookwebsite.service;

import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.repository.CategoryRepository;
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
                .map(CategoryDTO::new)
                .toList();
    }

    // Create category
    @Override
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
