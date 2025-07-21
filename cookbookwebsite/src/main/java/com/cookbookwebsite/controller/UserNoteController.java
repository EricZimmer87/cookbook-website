package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.service.UserNoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-notes")
public class UserNoteController {
    private final UserNoteService userNoteService;

    public UserNoteController(UserNoteService userNoteService) {
        this.userNoteService = userNoteService;
    }

    // GET /api/user-notes/user/{userId}
    @GetMapping("/user/{userId}")
    public List<UserNoteDTO> getNotesByUserId(@PathVariable Integer userId) {
        return userNoteService.getNotesByUserId(userId);
    }

    // GET /api/user-notes/recipe/{recipeId}
    @GetMapping("/recipe/{recipeId}")
    public List<UserNoteDTO> getNotesByRecipeId(@PathVariable Integer recipeId) {
        return userNoteService.getNotesByRecipeId(recipeId);
    }

    // POST /api/user-notes
    @PostMapping
    public UserNote createUserNote(@RequestBody UserNote userNote) {
        return userNoteService.createUserNote(userNote);
    }
}
