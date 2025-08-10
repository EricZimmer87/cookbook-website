package com.cookbookwebsite.controller;
import com.cookbookwebsite.dto.userfavorite.CreateFavoriteRequest;
import com.cookbookwebsite.dto.userfavorite.UserFavoriteDTO;
import com.cookbookwebsite.model.UserFavorite;
import com.cookbookwebsite.service.UserFavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("#userId == principal.userId or hasRole('ADMIN')")
    public List<UserFavoriteDTO> getFavoritesByUserId(@PathVariable Integer userId) {
        return userFavoriteService.getFavoritesByUserId(userId);
    }

    // GET all users who have the recipe favorited /api/user-favorites/recipe/{recipeId}
    @GetMapping("/recipe/{recipeId}")
    public List<UserFavoriteDTO> getFavoritesByRecipeId(@PathVariable Integer recipeId) {
        return userFavoriteService.getFavoritesByRecipeId(recipeId);
    }

    // GET /api/user-favorites/user/{userId}/recipe/{recipeId}
    @GetMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("#userId == principal.userId or hasRole('ADMIN')")
    public boolean isRecipeFavoritedByUser(@PathVariable Integer userId, @PathVariable Integer recipeId) {
        return userFavoriteService.isRecipeFavoritedByUser(userId, recipeId);
    }

    // POST /api/user-favorites
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content
    @PreAuthorize("#request.userId == principal.userId or hasRole('ADMIN')")
    public void createUserFavorite(@RequestBody CreateFavoriteRequest request) {
        userFavoriteService.createUserFavorite(request.getUserId(), request.getRecipeId());
    }

    // DELETE /api/user-favorites/user/{userId}/recipe/{recipeId}
    @DeleteMapping("/user/{userId}/recipe/{recipeId}")
    @PreAuthorize("#userId == principal.userId or hasRole('ADMIN')")
    public void removeUserFavorite(@PathVariable Integer userId, @PathVariable Integer recipeId) {
        userFavoriteService.removeUserFavorite(userId, recipeId);
    }
}
