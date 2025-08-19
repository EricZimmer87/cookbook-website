package com.cookbookwebsite.dto.recipe;

public record IngredientRow(
        Integer ingredientId,
        String quantity,
        String unit,
        Boolean isOptional
) {}
