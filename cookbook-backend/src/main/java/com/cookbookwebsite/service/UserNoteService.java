package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;

import java.util.List;

public interface UserNoteService {
    List<UserNoteDTO> getNotesByUserId(Integer userId);
    List<UserNoteDTO> getNotesByRecipeId(Integer recipeId);
    void createUserNote(Integer userId, Integer recipeId, String noteText);
    UserNoteDTO getNoteByUserIdAndRecipeId(Integer userId, Integer recipeId);
    void deleteUserNote(Integer userId, Integer recipeId);
}
