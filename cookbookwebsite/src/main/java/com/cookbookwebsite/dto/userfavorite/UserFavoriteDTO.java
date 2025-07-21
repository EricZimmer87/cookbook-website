package com.cookbookwebsite.dto.userfavorite;

import com.cookbookwebsite.model.UserFavorite;

public class UserFavoriteDTO {
    private Integer userId;
    private Integer recipeId;
    private String userName;
    private String recipeName;

    public UserFavoriteDTO(UserFavorite userFavorite) {
        this.userId = userFavorite.getUser().getUserId();
        this.recipeId = userFavorite.getRecipe().getRecipeId();
        this.userName = userFavorite.getUser().getUserName();
        this.recipeName = userFavorite.getRecipe().getRecipeName();
    }

    // Getters
    public Integer getUserId() { return userId; }
    public Integer getRecipeId() { return recipeId; }
    public String getUserName() { return userName; }
    public String getRecipeName() { return recipeName; }
}
