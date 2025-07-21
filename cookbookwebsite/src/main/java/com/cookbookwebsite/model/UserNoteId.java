package com.cookbookwebsite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserNoteId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "recipe_id")
    private Integer recipeId;

    public UserNoteId() {}

    public UserNoteId(Integer userId, Integer recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    // Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserNoteId that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(recipeId, that.recipeId);
    }

    // hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(userId, recipeId);
    }
}
