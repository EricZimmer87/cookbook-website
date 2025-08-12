package com.cookbookwebsite.dto.recipeingredient;

import com.cookbookwebsite.model.RecipeIngredient;

public class RecipeIngredientDTO {
    private Integer recipeId;
    private Integer ingredientId;
    private Double quantity;
    private String unit;
    private String recipeName;
    private String ingredientName;
    private Boolean isOptional;

    public RecipeIngredientDTO(RecipeIngredient recipeIngredient) {
        this.recipeId = recipeIngredient.getRecipe().getRecipeId();
        this.ingredientId = recipeIngredient.getIngredient().getIngredientId();
        this.quantity = recipeIngredient.getQuantity();
        this.unit = recipeIngredient.getUnit();
        this.recipeName = recipeIngredient.getRecipe().getRecipeName();
        this.ingredientName = recipeIngredient.getIngredient().getIngredientName();
        this.isOptional = recipeIngredient.getIsOptional();
    }

    // Getters
    public Integer getRecipeId() { return recipeId; }
    public Integer getIngredientId() { return ingredientId; }
    public Double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public String getRecipeName() { return recipeName; }
    public String getIngredientName() { return ingredientName; }
    public Boolean getIsOptional() { return isOptional; }
}
