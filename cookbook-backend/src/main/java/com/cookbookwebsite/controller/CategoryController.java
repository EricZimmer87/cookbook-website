package com.cookbookwebsite.controller;

import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.dto.category.CategoryDTO;
import com.cookbookwebsite.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasRole('ADMIN')") // Only allow admin access
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping
    public List<CategoryDTO> getAllCategoryDTOs() {
        return categoryService.getAllCategoryDTOs();
    }

    // Get category by ID
    @GetMapping("/{categoryId}")
    public CategoryDTO getCategoryById(@PathVariable Integer categoryId) { return categoryService.getCategoryById(categoryId); }

    // Update category
    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Integer id, @RequestBody Category updatedCategory) {
        Category category = categoryService.getCategoryEntityById(id);

        if (updatedCategory.getCategoryName() != null) {
            category.setCategoryName(updatedCategory.getCategoryName());
        }

        Category savedCategory = categoryService.saveCategory(category);
        return new CategoryDTO(savedCategory, savedCategory.getRecipes() != null ? savedCategory.getRecipes().size() : 0);
    }

    // Create new category
    @PostMapping
    public CategoryDTO createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.createCategory(category);
        return new CategoryDTO(
                savedCategory,
                savedCategory.getRecipes() != null ? savedCategory.getRecipes().size() : 0
        );
    }

    // Delete category
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) { categoryService.deleteCategoryById(categoryId); }
}
