package com.cookbookwebsite.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_notes")
public class UserNote {
    @EmbeddedId
    private UserNoteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "note_text", nullable = false)
    private String noteText;

    public UserNote() {}

    public UserNote(User user, Recipe recipe, String noteText) {
        this.user = user;
        this.recipe = recipe;
        this.noteText = noteText;
        this.id = new UserNoteId(user.getUserId(), recipe.getRecipeId());
    }

    // Getters and Setters
    public UserNoteId getId() { return id; }
    public void setId(UserNoteId id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public String getNoteText() { return noteText; }
    public void setNoteText(String noteText) { this.noteText = noteText; }
}
