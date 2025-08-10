package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.recipetag.RecipeTagDTO;
import com.cookbookwebsite.model.RecipeTag;
import com.cookbookwebsite.service.RecipeTagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-tags")
@PreAuthorize("hasRole('ADMIN')")
public class RecipeTagController {
    private final RecipeTagService recipeTagService;

    public RecipeTagController(RecipeTagService recipeTagService) {
        this.recipeTagService = recipeTagService;
    }

    @GetMapping
    public List<RecipeTagDTO> getAllRecipeTagDTOs() {
        return recipeTagService.getAllRecipeTagDTOs();
    }

    @PostMapping
    public RecipeTag createRecipeTag(@RequestBody RecipeTag recipeTag) {
        return recipeTagService.createRecipeTag(recipeTag);
    }
}
