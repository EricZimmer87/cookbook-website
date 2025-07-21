package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipeingredient.RecipeIngredientDTO;
import com.cookbookwebsite.model.RecipeIngredient;
import com.cookbookwebsite.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeIngredientDTO> getAllRecipeIngredientDTOs() {
        return recipeIngredientRepository.findAll()
                .stream()
                .map(RecipeIngredientDTO::new)
                .toList();
    }

    @Override
    public RecipeIngredient createRecipeIngredient(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }
}
