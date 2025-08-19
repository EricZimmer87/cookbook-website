package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserUserId(Integer userId);
    List<Review> findByRecipeRecipeId(Integer recipeId);
    void deleteByRecipe_RecipeId(Integer recipeId);
    boolean existsByUser_UserIdAndRecipe_RecipeId(Integer userId, Integer recipeId);
    Optional<Review> findByUser_UserIdAndRecipe_RecipeId(Integer userId, Integer recipeId);
}
