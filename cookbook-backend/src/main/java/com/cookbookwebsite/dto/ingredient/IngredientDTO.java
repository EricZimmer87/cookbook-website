package com.cookbookwebsite.dto.ingredient;

import com.cookbookwebsite.model.Ingredient;

public class IngredientDTO {
    private Integer ingredientId;
    private String ingredientName;

    public IngredientDTO(Ingredient ingredient) {
        this.ingredientId = ingredient.getIngredientId();
        this.ingredientName = ingredient.getIngredientName();
    }

    // Getters
    public Integer getIngredientId() { return ingredientId; }
    public String getIngredientName() { return ingredientName; }
}
