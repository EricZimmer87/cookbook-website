package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.userfavorite.UserFavoriteDTO;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.model.UserFavorite;
import com.cookbookwebsite.repository.RecipeRepository;
import com.cookbookwebsite.repository.UserFavoriteRepository;
import com.cookbookwebsite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {
    private final UserFavoriteRepository userFavoriteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public UserFavoriteServiceImpl(
            UserFavoriteRepository userFavoriteRepository,
            UserRepository userRepository,
            RecipeRepository recipeRepository) {
        this.userFavoriteRepository = userFavoriteRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    // Get favorites by user ID
    @Override
    @Transactional(readOnly = true)
    public List<UserFavoriteDTO> getFavoritesByUserId(Integer userId) {
        return userFavoriteRepository.findByUserUserId(userId)
                .stream()
                .map(UserFavoriteDTO::new)
                .toList();
    }

    // Get favorites by recipe ID
    @Override
    @Transactional(readOnly = true)
    public List<UserFavoriteDTO> getFavoritesByRecipeId(Integer recipeId) {
        return userFavoriteRepository.findByRecipeRecipeId(recipeId)
                .stream()
                .map(UserFavoriteDTO::new)
                .toList();
    }

    // Create user favorite
    @Override
    @Transactional
    public UserFavorite createUserFavorite(Integer userId, Integer recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        UserFavorite userFavorite = new UserFavorite(user, recipe);
        return userFavoriteRepository.save(userFavorite);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRecipeFavoritedByUser(Integer userId, Integer recipeId) {
        return userFavoriteRepository.existsByUserUserIdAndRecipeRecipeId(userId, recipeId);
    }

    // Remove user favorite
    @Override
    @Transactional
    public void removeUserFavorite(Integer userId, Integer recipeId) {
        userFavoriteRepository.deleteByUserUserIdAndRecipeRecipeId(userId, recipeId);
    }
}
