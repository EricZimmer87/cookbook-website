package com.cookbookwebsite.dto.usernote;

import com.cookbookwebsite.model.UserNote;

public class UserNoteDTO {
    private Integer userId;
    private Integer recipeId;
    private String userName;
    private String recipeName;
    private String noteText;

    public UserNoteDTO(UserNote userNote) {
        this.userId = userNote.getUser().getUserId();
        this.recipeId = userNote.getRecipe().getRecipeId();
        this.userName = userNote.getUser().getUserName();
        this.recipeName = userNote.getRecipe().getRecipeName();
        this.noteText = userNote.getNoteText();
    }

    // Getters
    public Integer getUserId() { return userId; }
    public Integer getRecipeId() { return recipeId; }
    public String getUserName() { return userName; }
    public String getRecipeName() { return recipeName; }
    public String getNoteText() { return noteText; }
}
