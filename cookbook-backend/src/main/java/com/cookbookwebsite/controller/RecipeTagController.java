package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.recipetag.RecipeTagDTO;
import com.cookbookwebsite.model.RecipeTag;
import com.cookbookwebsite.service.RecipeTagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-tags")
public class RecipeTagController {
    private final RecipeTagService recipeTagService;

    public RecipeTagController(RecipeTagService recipeTagService) {
        this.recipeTagService = recipeTagService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    public List<RecipeTagDTO> getAllRecipeTagDTOs() {
        return recipeTagService.getAllRecipeTagDTOs();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RecipeTag createRecipeTag(@RequestBody RecipeTag recipeTag) {
        return recipeTagService.createRecipeTag(recipeTag);
    }
}
