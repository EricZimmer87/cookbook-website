package com.cookbookwebsite.dto.userfavorite;

// Create this DTO class
public class CreateFavoriteRequest {
    private Integer userId;
    private Integer recipeId;

    // Constructors, getters, and setters
    public CreateFavoriteRequest() {}

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }
}