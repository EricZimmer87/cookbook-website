package com.cookbookwebsite.controller;
import com.cookbookwebsite.dto.userfavorite.UserFavoriteDTO;
import com.cookbookwebsite.model.UserFavorite;
import com.cookbookwebsite.service.UserFavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-favorites")
public class UserFavoriteController {
    private final UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserFavoriteService userFavoriteService) {
        this.userFavoriteService = userFavoriteService;
    }

    // GET /api/user-favorites/user/{userId}
    @GetMapping("/user/{userId}")
    public List<UserFavoriteDTO> getFavoritesByUserId(@PathVariable Integer userId) {
        return userFavoriteService.getFavoritesByUserId(userId);
    }

    // GET /api/user-favorites/recipe/{recipeId}
    @GetMapping("/recipe/{recipeId}")
    public List<UserFavoriteDTO> getFavoritesByrecipeId(@PathVariable Integer recipeId) {
        return userFavoriteService.getFavoritesByRecipeId(recipeId);
    }

    // POST /api/user-favorites
    @PostMapping
    public UserFavorite createUserFavoriet(@RequestBody UserFavorite userFavorite) {
        return userFavoriteService.createUserFavorite(userFavorite);
    }
}
