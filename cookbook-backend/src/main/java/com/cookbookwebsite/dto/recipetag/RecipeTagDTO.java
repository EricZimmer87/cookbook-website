package com.cookbookwebsite.dto.recipetag;

import com.cookbookwebsite.model.RecipeTag;

public class RecipeTagDTO {
    private Integer recipeId;
    private String recipeName;
    private Integer tagId;
    private String tagName;

    public RecipeTagDTO(RecipeTag recipeTag) {
        this.recipeId = recipeTag.getRecipe().getRecipeId();
        this.recipeName = recipeTag.getRecipe().getRecipeName();
        this.tagId = recipeTag.getTag().getTagId();
        this.tagName = recipeTag.getTag().getTagName();
    }

    // Getters
    public Integer getRecipeId() { return recipeId; }
    public String getRecipeName() { return recipeName; }
    public Integer getTagId() { return tagId; }
    public String getTagName() { return tagName; }
}
