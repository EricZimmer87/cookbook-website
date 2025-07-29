package com.cookbookwebsite.dto.category;

import com.cookbookwebsite.model.Category;

public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;
    private Integer recipeCount;

    public CategoryDTO(Category category, int recipeCount) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.recipeCount = recipeCount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}