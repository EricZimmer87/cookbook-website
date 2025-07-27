package com.cookbookwebsite.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_favorites")
public class UserFavorite {
    @EmbeddedId
    private UserFavoriteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public UserFavorite() {}

    public UserFavorite(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
        this.id = new UserFavoriteId(user.getUserId(), recipe.getRecipeId());
    }

    // Getters and Setters
    public UserFavoriteId getId() { return id; }
    public void setId(UserFavoriteId id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
}
