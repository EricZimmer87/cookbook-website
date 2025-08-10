package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.ingredient.IngredientDTO;
import com.cookbookwebsite.exception.ResourceInUseException;
import com.cookbookwebsite.model.Ingredient;
import com.cookbookwebsite.repository.IngredientRepository;
import com.cookbookwebsite.repository.RecipeIngredientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredientDTOs() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDTO getIngredientById(Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + ingredientId));
        return new IngredientDTO(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient getIngredientEntityById(Integer ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + ingredientId));
    }

    @Override
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public void deleteIngredientById(Integer ingredientId) {
        // App-level guard — avoids hitting FK error and lets us return a friendly message
        if (recipeIngredientRepository.existsByIngredient_IngredientId(ingredientId)) {
            throw new ResourceInUseException("Cannot delete ingredient: it is used by one or more recipes.");
        }

        try {
            ingredientRepository.deleteById(ingredientId);
        } catch (DataIntegrityViolationException ex) {
            // Safety net in case of race conditions / FK constraints
            throw new ResourceInUseException("Cannot delete ingredient: it is used by one or more recipes.");
        }
    }
}