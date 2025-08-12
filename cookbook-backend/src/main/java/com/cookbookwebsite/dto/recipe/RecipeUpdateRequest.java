package com.cookbookwebsite.dto.recipe;

import java.util.List;

public record RecipeUpdateRequest(
        String recipeName,
        String recipeInstructions,
        Integer categoryId,
        Integer difficultyId,
        List<IngredientRow> ingredients,   // may be null/empty
        List<Integer> tagIds               // may be null/empty
) {}