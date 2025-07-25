package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipe.RecipeDTO;
import com.cookbookwebsite.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<RecipeDTO> getAllRecipeDTOs();
    RecipeDTO getRecipeById(Integer id);
    Recipe createRecipe(Recipe recipe);
    List<RecipeDTO> getRecipesByUserId(Integer userId);
}
