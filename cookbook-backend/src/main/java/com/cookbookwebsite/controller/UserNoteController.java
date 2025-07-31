package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.security.AuthService;
import com.cookbookwebsite.security.CustomUserDetails;
import com.cookbookwebsite.service.UserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-notes")
public class UserNoteController {
    private final UserNoteService userNoteService;
    @Autowired
    private AuthService authService;

    public UserNoteController(UserNoteService userNoteService) {
        this.userNoteService = userNoteService;
    }

    // Get user notes for recipe ID from logged in user
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<UserNoteDTO> getNoteByLoggedInUserForRecipe(@PathVariable Integer recipeId) {
        Integer userId = authService.getCurrentUserId();
        UserNoteDTO note = userNoteService.getNoteByUserIdAndRecipeId(userId, recipeId);
        return (note != null)
                ? ResponseEntity.ok(note)
                : ResponseEntity.noContent().build();
    }


    // Create user note /api/user-notes
    @PostMapping
    public UserNote createUserNote(@RequestBody UserNote userNote) {
        return userNoteService.createUserNote(userNote);
    }
}
