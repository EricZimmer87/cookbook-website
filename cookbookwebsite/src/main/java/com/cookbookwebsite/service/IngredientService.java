package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.ingredient.IngredientDTO;
import com.cookbookwebsite.model.Ingredient;

import java.util.List;

public interface IngredientService {
    List<IngredientDTO> getAllIngredientDTOs();
    Ingredient createIngredient(Ingredient ingredient);
}
