package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipeingredient.RecipeIngredientDTO;
import com.cookbookwebsite.model.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientService {
    List<RecipeIngredientDTO> getAllRecipeIngredientDTOs();
    RecipeIngredient createRecipeIngredient(RecipeIngredient recipeIngredient);
}
