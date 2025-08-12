package com.cookbookwebsite.dto.recipe;

public record IngredientRow(
        Integer ingredientId,
        Double quantity,
        String unit,
        Boolean isOptional
) {}
