package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipetag.RecipeTagDTO;
import com.cookbookwebsite.model.RecipeTag;

import java.util.List;

public interface RecipeTagService {
    List<RecipeTagDTO> getAllRecipeTagDTOs();
    RecipeTag createRecipeTag(RecipeTag recipeTag);
}
