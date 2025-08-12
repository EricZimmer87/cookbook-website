package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.recipe.RecipeCreateRequest;
import com.cookbookwebsite.dto.recipe.RecipeDTO;
import com.cookbookwebsite.dto.recipe.RecipeUpdateRequest;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Get all recipes, or filter by userId if provided
    @GetMapping
    public List<RecipeDTO> getRecipes(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return recipeService.getRecipesByUserId(userId);
        } else {
            return recipeService.getAllRecipeDTOs();
        }
    }

    // Get recipe by recipe ID
    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable Integer id) {
        return recipeService.getRecipeById(id);
    }

    // Create recipe
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeCreateRequest req) {
        var saved = recipeService.createRecipeForCurrentUser(req);
        var location = URI.create("/api/recipes/" + saved.getRecipeId());
        return ResponseEntity.created(location).body(new RecipeDTO(saved));
    }

    // Update recipe
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PutMapping("/{id}")
    public RecipeDTO updateRecipe(
            @PathVariable Integer id,
            @RequestBody RecipeUpdateRequest req
    ) {
        return recipeService.updateRecipeForCurrentUser(id, req);
    }

    // Delete recipe
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipeForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }
}
