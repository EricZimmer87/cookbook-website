package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.recipe.RecipeDTO;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Get all recipes, or filter by userId if provided
    @GetMapping
    public List<RecipeDTO> getRecipes(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return recipeService.getRecipesByUserId(userId);
        } else {
            return recipeService.getAllRecipeDTOs();
        }
    }

    // Get recipe by recipe ID
    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable Integer id) {
        return recipeService.getRecipeById(id);
    }

    // Create recipe
    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
}
