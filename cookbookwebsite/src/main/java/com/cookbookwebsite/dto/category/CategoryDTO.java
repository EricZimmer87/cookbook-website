package com.cookbookwebsite.dto.category;

import com.cookbookwebsite.model.Category;

public class CategoryDTO {
    private Integer id;
    private String name;
    private Integer recipeCount;

    public CategoryDTO(Category category) {
        this.id = category.getCategoryId();
        this.name = category.getCategoryName();
        this.recipeCount = category.getRecipes() != null ? category.getRecipes().size() : 0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}