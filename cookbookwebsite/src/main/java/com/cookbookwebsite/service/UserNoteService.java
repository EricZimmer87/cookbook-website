package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;

import java.util.List;

public interface UserNoteService {
    List<UserNoteDTO> getNotesByUserId(Integer userId);
    List<UserNoteDTO> getNotesByRecipeId(Integer recipeId);
    UserNote createUserNote(UserNote userNote);
}
