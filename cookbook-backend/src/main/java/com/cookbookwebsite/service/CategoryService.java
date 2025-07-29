package com.cookbookwebsite.service;

import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.dto.category.CategoryDTO;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<CategoryDTO> getAllCategoryDTOs();
    CategoryDTO getCategoryById(Integer categoryId);
    Category saveCategory(Category category);
    Category getCategoryEntityById(Integer id);
    void deleteCategoryById(Integer categoryId);
}
