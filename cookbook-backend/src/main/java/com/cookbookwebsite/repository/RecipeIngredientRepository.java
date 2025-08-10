package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.RecipeIngredient;
import com.cookbookwebsite.model.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
    boolean existsByIngredient_IngredientId(Integer ingredientId);
}