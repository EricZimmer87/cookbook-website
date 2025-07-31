package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.review.ReviewCreateDTO;
import com.cookbookwebsite.dto.review.ReviewDTO;
import com.cookbookwebsite.model.Recipe;
import com.cookbookwebsite.model.Review;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RecipeRepository;
import com.cookbookwebsite.repository.ReviewRepository;
import com.cookbookwebsite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            RecipeRepository recipeRepository,
            UserRepository userRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    // Get reviews by user ID
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByUserId(Integer userId) {
        return reviewRepository.findByUserUserId(userId)
                .stream()
                .map(ReviewDTO::new)
                .toList();
    }

    // Get reviews by recipe ID
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByRecipeId(Integer recipeId) {
        return reviewRepository.findByRecipeRecipeId(recipeId)
                .stream()
                .map(ReviewDTO::new)
                .toList();
    }

    // Get a single review by review ID
    @Override
    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));
        return new ReviewDTO(review);
    }

    // PUT update a review
    @Override
    @Transactional
    public Review updateReview(Integer reviewId, Review updatedReview) {
        Review existing = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        existing.setScore(updatedReview.getScore());
        existing.setReviewText(updatedReview.getReviewText());

        return reviewRepository.save(existing);
    }

    // Create a review
    @Override
    public ReviewDTO createReview(ReviewCreateDTO dto, Integer userId) {
        Review review = new Review();

        // Assuming you have User and Recipe objects to set
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        review.setUser(user);
        review.setRecipe(recipe);
        review.setScore(dto.getScore());
        review.setReviewText(dto.getReviewText());

        Review saved = reviewRepository.save(review);

        // Manually create ReviewDTO to return
        return new ReviewDTO(saved);
    }



    // Delete review
    @Override
    @Transactional
    public void deleteReviewById(Integer reviewId) { reviewRepository.deleteById(reviewId); }
}
