package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.repository.UserNoteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNoteServiceImpl implements UserNoteService {

    private final UserNoteRepository userNoteRepository;

    public UserNoteServiceImpl(UserNoteRepository userNoteRepository) {
        this.userNoteRepository = userNoteRepository;
    }

    // Get notes by user ID
    @Override
    @Transactional(readOnly = true)
    public List<UserNoteDTO> getNotesByUserId(Integer userId) {
        return userNoteRepository
            .findByUserUserId(userId)
            .stream()
            .map(UserNoteDTO::new)
            .toList();
    }

    // Get notes by recipe ID
    @Override
    @Transactional(readOnly = true)
    public List<UserNoteDTO> getNotesByRecipeId(Integer recipeId) {
        return userNoteRepository
            .findByRecipeRecipeId(recipeId)
            .stream()
            .map(UserNoteDTO::new)
            .toList();
    }

    // Create a user note
    @Override
    @Transactional
    public UserNote createUserNote(UserNote userNote) {
        return userNoteRepository.save(userNote);
    }

    @Override
    public UserNoteDTO getNoteByUserIdAndRecipeId(Integer userId, Integer recipeId) {
        return userNoteRepository.findByUser_UserIdAndRecipe_RecipeId(userId, recipeId)
                .map(UserNoteDTO::new)  // uses your existing constructor
                .orElse(null);
    }
}
