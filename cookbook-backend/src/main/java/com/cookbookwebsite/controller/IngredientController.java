package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.ingredient.IngredientDTO;
import com.cookbookwebsite.model.Ingredient;
import com.cookbookwebsite.service.IngredientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@PreAuthorize("hasRole('ADMIN', 'CONTRIBUTOR')")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // Get all ingredients
    @GetMapping
    public List<IngredientDTO> getAllIngredientDTOs() {
        return ingredientService.getAllIngredientDTOs();
    }

    // Get ingredient by ID
    @GetMapping("/{ingredientId}")
    public IngredientDTO getIngredientById(@PathVariable Integer ingredientId) {
        return ingredientService.getIngredientById(ingredientId);
    }

    // Update ingredient
    @PutMapping("/{id}")
    public IngredientDTO updateIngredient(@PathVariable Integer id, @RequestBody Ingredient updatedIngredient) {
        Ingredient ingredient = ingredientService.getIngredientEntityById(id);

        if (updatedIngredient.getIngredientName() != null) {
            ingredient.setIngredientName(updatedIngredient.getIngredientName());
        }

        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);
        return new IngredientDTO(savedIngredient);
    }

    // Create ingredient
    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    // Delete ingredient
    @DeleteMapping("/{ingredientId}")
    public void deleteIngredient(@PathVariable Integer ingredientId) {
        ingredientService.deleteIngredientById(ingredientId);
    }
}
