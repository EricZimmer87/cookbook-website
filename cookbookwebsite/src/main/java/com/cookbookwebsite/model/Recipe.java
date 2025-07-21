package com.cookbookwebsite.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer recipeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "difficulty_id")
    private DifficultyLevel difficultyLevel;

    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Column(name = "recipe_instructions", columnDefinition = "TEXT", nullable = false)
    private String recipeInstructions;

    @Column(name = "recipe_image")
    private String recipeImage;

    // Getters and Setters
    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeInstructions() { return recipeInstructions; }
    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeImage() { return recipeImage; }
    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() { return category; }
    public void setCategory(Category category) {
        this.category = category;
    }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<RecipeIngredient> getRecipeIngredients() { return recipeIngredients; }
    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

}
