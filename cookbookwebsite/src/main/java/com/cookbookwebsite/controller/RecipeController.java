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

    @GetMapping
    public List<RecipeDTO> getAllRecipeDTOs() {
        return recipeService.getAllRecipeDTOs();
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable Integer id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
}
