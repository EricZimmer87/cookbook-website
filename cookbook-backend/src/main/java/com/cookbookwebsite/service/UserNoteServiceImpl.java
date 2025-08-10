package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.model.UserNoteId;
import com.cookbookwebsite.repository.RecipeRepository;
import com.cookbookwebsite.repository.UserNoteRepository;
import java.util.List;

import com.cookbookwebsite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNoteServiceImpl implements UserNoteService {

    private final UserNoteRepository userNoteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public UserNoteServiceImpl(
            UserNoteRepository userNoteRepository,
            UserRepository userRepository,
            RecipeRepository recipeRepository) {
        this.userNoteRepository = userNoteRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
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
    public void createUserNote(Integer userId, Integer recipeId, String noteText) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        var note = new UserNote();
        note.setUser(user);
        note.setRecipe(recipe);
        note.setNoteText(noteText);
        note.setId(new UserNoteId(user.getUserId(), recipe.getRecipeId())); // REQUIRED
        userNoteRepository.save(note);
    }

    @Override
    public UserNoteDTO getNoteByUserIdAndRecipeId(Integer userId, Integer recipeId) {
        return userNoteRepository.findByUser_UserIdAndRecipe_RecipeId(userId, recipeId)
                .map(UserNoteDTO::new)  // uses your existing constructor
                .orElse(null);
    }

    // UserNoteServiceImpl.java
    @Override
    @Transactional
    public void deleteUserNote(Integer userId, Integer recipeId) {
        userNoteRepository.deleteByUser_UserIdAndRecipe_RecipeId(userId, recipeId);
    }
}
