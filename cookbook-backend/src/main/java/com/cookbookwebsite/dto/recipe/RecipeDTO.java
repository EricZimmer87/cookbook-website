package com.cookbookwebsite.dto.recipe;

import com.cookbookwebsite.dto.recipetag.RecipeTagDTO;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.dto.recipeingredient.RecipeIngredientDTO;
import com.cookbookwebsite.model.RecipeIngredient;
import com.cookbookwebsite.model.RecipeTag;

import java.util.List;

public class RecipeDTO {
    private Integer recipeId;
    private Integer ownerId;
    private String recipeName;
    private String recipeInstructions;
    private String recipeImage;
    private Integer categoryId;
    private String categoryName;
    private Integer difficultyLevelId;
    private String difficultyLevel;
    private String userName;
    private List<RecipeIngredientDTO> recipeIngredients;
    private List<RecipeTagDTO> recipeTags;

    public RecipeDTO(Recipe recipe) {
        this.recipeId = recipe.getRecipeId();
        this.ownerId = recipe.getUser().getUserId();
        this.recipeName = recipe.getRecipeName();
        this.recipeInstructions = recipe.getRecipeInstructions();
        this.recipeImage = recipe.getRecipeImage();
        this.categoryId = recipe.getCategory().getCategoryId();
        this.categoryName = recipe.getCategory() != null ? recipe.getCategory().getCategoryName() : null;
        this.difficultyLevelId = recipe.getDifficultyLevel().getDifficultyId();
        this.difficultyLevel = recipe.getDifficultyLevel() != null ? recipe.getDifficultyLevel().getDifficultyLevelName() : null;
        this.userName = recipe.getUser().getUserName();
        List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
        this.recipeIngredients = (ingredients == null ? List.<RecipeIngredient>of() : ingredients)
                .stream().map(RecipeIngredientDTO::new).toList();
        List<RecipeTag> tags = recipe.getRecipeTags();
        this.recipeTags = (tags == null ? List.<RecipeTag>of() : tags)
                .stream().map(RecipeTagDTO::new).toList();
    }

    // Getters
    public Integer getRecipeId() { return recipeId; }
    public String getRecipeName() { return recipeName; }
    public String getRecipeInstructions() { return recipeInstructions; }
    public String getRecipeImage() { return recipeImage; }
    public Integer getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public Integer getDifficultyLevelId() { return difficultyLevelId; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public String getUserName() { return userName; }
    public List<RecipeIngredientDTO> getRecipeIngredients() { return recipeIngredients; }
    public List<RecipeTagDTO> getRecipeTags() { return recipeTags; }
    public Integer getOwnerId() { return ownerId; }
}