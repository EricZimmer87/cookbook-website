package com.cookbookwebsite.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeCreateRequest(
        @NotBlank String recipeName,
        @NotBlank String recipeInstructions,
        @NotNull  Integer categoryId,         // required
        @NotNull  Integer difficultyId,       // required
        List<IngredientRow> ingredients,
        List<Integer> tagIds
) {}