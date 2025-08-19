package com.cookbookwebsite.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "is_optional")
    private Boolean isOptional;

    public RecipeIngredient() {}

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity, String unit, Boolean isOptional) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.isOptional = isOptional;
        this.id = new RecipeIngredientId(recipe.getRecipeId(), ingredient.getIngredientId());
    }

    // Getters and Setters
    public RecipeIngredientId getId() { return id; }
    public void setId(RecipeIngredientId id) {
        this.id = id;
    }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() { return unit; }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getIsOptional() { return isOptional; }
    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
}
