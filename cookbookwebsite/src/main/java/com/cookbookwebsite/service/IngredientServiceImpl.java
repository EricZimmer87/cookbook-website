package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.ingredient.IngredientDTO;
import com.cookbookwebsite.model.Ingredient;
import com.cookbookwebsite.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository= ingredientRepository;
    }

    // Get all ingredients
    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredientDTOs() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientDTO::new)
                .toList();
    }

    // Create ingredient
    @Override
    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
