package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.recipeingredient.RecipeIngredientDTO;
import com.cookbookwebsite.model.RecipeIngredient;
import com.cookbookwebsite.service.RecipeIngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {
    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @GetMapping
    public List<RecipeIngredientDTO> getAllRecipeIngredientDTOs() {
        return recipeIngredientService.getAllRecipeIngredientDTOs();
    }

    @PostMapping
    public RecipeIngredient createRecipeIngredient(@RequestBody RecipeIngredient recipeIngredient) {
        return recipeIngredientService.createRecipeIngredient(recipeIngredient);
    }
}
