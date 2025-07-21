package com.cookbookwebsite.controller;

import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.dto.category.CategoryDTO;
import com.cookbookwebsite.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAllCategoryDTOs() {
        return categoryService.getAllCategoryDTOs();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
}
