package com.cookbookwebsite.service;

import com.cookbookwebsite.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe createRecipe(Recipe recipe);
}
