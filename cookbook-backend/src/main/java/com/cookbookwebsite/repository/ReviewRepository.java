package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserUserId(Integer userId);
    List<Review> findByRecipeRecipeId(Integer recipeId);
}
