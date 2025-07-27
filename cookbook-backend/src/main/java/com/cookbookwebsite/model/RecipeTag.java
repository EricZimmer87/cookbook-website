package com.cookbookwebsite.model;

import com.cookbookwebsite.model.RecipeTagId;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_tags")
public class RecipeTag {
    @EmbeddedId
    private RecipeTagId id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    //Constructors
    public RecipeTag() {}

    public RecipeTag(Recipe recipe, Tag tag) {
        this.recipe = recipe;
        this.tag = tag;
        this.id = new RecipeTagId(recipe.getRecipeId(), tag.getTagId());
    }

    // Getters and Setters
    public RecipeTagId getId() { return id; }
    public void setId(RecipeTagId id) { this.id = id; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public Tag getTag() { return tag; }
    public void setTag(Tag tag) { this.tag = tag; }
}
