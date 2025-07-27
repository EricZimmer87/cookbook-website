package com.cookbookwebsite.dto.recipe;

import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.dto.recipeingredient.RecipeIngredientDTO;

import java.util.List;

public class RecipeDTO {
    private Integer recipeId;
    private String recipeName;
    private String recipeInstructions;
    private String recipeImage;
    private String categoryName;
    private String difficultyLevel;
    private String userName;
    private List<RecipeIngredientDTO> recipeIngredients;

    public RecipeDTO(Recipe recipe) {
        this.recipeId = recipe.getRecipeId();
        this.recipeName = recipe.getRecipeName();
        this.recipeInstructions = recipe.getRecipeInstructions();
        this.recipeImage = recipe.getRecipeImage();
        this.categoryName = recipe.getCategory() != null ? recipe.getCategory().getCategoryName() : null;
        this.difficultyLevel = recipe.getDifficultyLevel() != null ? recipe.getDifficultyLevel().getDifficultyLevel() : null;
        this.userName = recipe.getUser().getUserName();
        this.recipeIngredients = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeIngredientDTO::new)
                .toList();
    }

    // Getters only (no setters for DTOs unless needed)
    public Integer getRecipeId() { return recipeId; }
    public String getRecipeName() { return recipeName; }
    public String getRecipeInstructions() { return recipeInstructions; }
    public String getRecipeImage() { return recipeImage; }
    public String getCategoryName() { return categoryName; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public String getUserName() { return userName; }
    public List<RecipeIngredientDTO> getRecipeIngredients() { return recipeIngredients; }
}