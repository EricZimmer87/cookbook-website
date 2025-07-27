package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipetag.RecipeTagDTO;
import com.cookbookwebsite.model.RecipeTag;
import com.cookbookwebsite.repository.RecipeTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeTagServiceImpl implements RecipeTagService {
    private final RecipeTagRepository recipeTagRepository;

    public RecipeTagServiceImpl(RecipeTagRepository recipeTagRepository) {
        this.recipeTagRepository = recipeTagRepository;
    }

    // Get all recipe tags
    @Override
    @Transactional(readOnly = true)
    public List<RecipeTagDTO> getAllRecipeTagDTOs() {
        return recipeTagRepository.findAll()
                .stream()
                .map(RecipeTagDTO::new)
                .toList();
    }

    // Create recipe tag
    @Override
    @Transactional
    public RecipeTag createRecipeTag(RecipeTag recipeTag) {
        return recipeTagRepository.save(recipeTag);
    }
}
