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

    // Get all ingredient DTOs
    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredientDTOs() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientDTO::new)
                .toList();
    }

    // Get ingredientDTO by ingredient ID
    @Override
    @Transactional(readOnly = true)
    public IngredientDTO getIngredientById(Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + ingredientId));
        return new IngredientDTO(ingredient);
    }

    // Get tag entity by tag ID
    @Override
    @Transactional(readOnly = true)
    public Ingredient getIngredientEntityById(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found."));
    }

    // Update ingredient
    @Override
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    // Create ingredient
    @Override
    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    // Delete ingredient
    @Override
    @Transactional
    public void deleteIngredientById(Integer ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
