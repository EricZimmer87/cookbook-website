package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.usernote.CreateUserNoteRequest;
import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.security.AuthService;
import com.cookbookwebsite.security.CustomUserDetails;
import com.cookbookwebsite.service.UserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-notes")
public class UserNoteController {
    private final UserNoteService userNoteService;
    private final AuthService authService;

    public UserNoteController(UserNoteService userNoteService, AuthService authService) {
        this.userNoteService = userNoteService;
        this.authService = authService;
    }

    // Get user's note for recipe
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recipe/{recipeId}/me")
    public ResponseEntity<UserNoteDTO> getMyNote(@PathVariable Integer recipeId) {
        Integer userId = authService.getCurrentUserId();
        var note = userNoteService.getNoteByUserIdAndRecipeId(userId, recipeId);
        return (note != null) ? ResponseEntity.ok(note) : ResponseEntity.noContent().build();
    }

    // b) Get a specific user's note for a recipe
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recipe/{recipeId}/user/{userId}")
    public ResponseEntity<UserNoteDTO> getUserNote(
            @PathVariable Integer recipeId,
            @PathVariable Integer userId
    ) {
        var note = userNoteService.getNoteByUserIdAndRecipeId(userId, recipeId);
        return (note != null) ? ResponseEntity.ok(note) : ResponseEntity.noContent().build();
    }

    // Get all notes for a recipe
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recipe/{recipeId}")
    public List<UserNoteDTO> getAllNotesForRecipe(@PathVariable Integer recipeId) {
        return userNoteService.getNotesByRecipeId(recipeId);
    }

    // 2) Create/Update note
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upsertMyNote(
            @PathVariable Integer recipeId,
            @RequestBody NoteRequest body,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        Integer userId = principal.getUserId();
        userNoteService.createUserNote(userId, recipeId, body.noteText());
    }

    // b) Admin (or owner) can upsert a note for any user
    @PreAuthorize("#userId == principal.userId or hasRole('ADMIN')")
    @PostMapping("/recipe/{recipeId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upsertNoteForUser(
            @PathVariable Integer recipeId,
            @PathVariable Integer userId,
            @RequestBody NoteRequest body
    ) {
        userNoteService.createUserNote(userId, recipeId, body.noteText());
    }

    // Delete note
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/recipe/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyNote(
            @PathVariable Integer recipeId,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        userNoteService.deleteUserNote(principal.getUserId(), recipeId);
    }

    // Owner or Admin can delete any user’s note
    @PreAuthorize("#userId == principal.userId or hasRole('ADMIN')")
    @DeleteMapping("/recipe/{recipeId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserNote(
            @PathVariable Integer recipeId,
            @PathVariable Integer userId
    ) {
        userNoteService.deleteUserNote(userId, recipeId);
    }

    public record NoteRequest(String noteText) {}
}
