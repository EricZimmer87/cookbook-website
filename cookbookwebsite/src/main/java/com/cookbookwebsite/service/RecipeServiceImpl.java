package com.cookbookwebsite.service;

import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.dto.recipe.RecipeDTO;
import com.cookbookwebsite.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getAllRecipeDTOs() {
        return recipeRepository.findAll()
                .stream()
                .map(RecipeDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeById(Integer id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));
        return new RecipeDTO(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getRecipesByUserId(Integer userId) {
        return recipeRepository.findByUserUserId(userId)
                .stream()
                .map(RecipeDTO::new)
                .toList();
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
