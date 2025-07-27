package com.cookbookwebsite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeTagId implements Serializable {
    @Column(name = "recipe_id")
    private Integer recipeId;

    @Column(name = "tag_id")
    private Integer tagId;

    // Constructors
    public RecipeTagId() {}

    public RecipeTagId(Integer recipeId, Integer tagId) {
        this.recipeId = recipeId;
        this.tagId = tagId;
    }

    // Getters and Setters
    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getTagId() { return tagId; }
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeTagId)) return false;
        RecipeTagId that = (RecipeTagId) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode () {
        return Objects.hash(recipeId, tagId);
    }
}
