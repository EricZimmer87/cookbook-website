package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    boolean existsByIngredientName(String ingredientName);
}
