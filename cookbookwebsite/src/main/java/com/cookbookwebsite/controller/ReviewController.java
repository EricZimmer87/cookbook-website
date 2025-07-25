package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.review.ReviewDTO;
import com.cookbookwebsite.model.Review;
import com.cookbookwebsite.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET reviews by user ID - /api/reviews/user/userId
    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable Integer userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    // GET reviews by recipe ID - /api/reviews/recipe/recipeId
    @GetMapping("/recipe/{recipeId}")
    public List<ReviewDTO> getReviewsByRecipeId(@PathVariable Integer recipeId) {
        return reviewService.getReviewsByRecipeId(recipeId);
    }

    // GET single review by review ID
    @GetMapping("/{reviewId}")
    public ReviewDTO getReviewById(@PathVariable Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    // PUT update single review
    @PutMapping("/{reviewId}")
    public ReviewDTO updateReview(@PathVariable Integer reviewId, @RequestBody Review review) {
        Review updated = reviewService.updateReview(reviewId, review);
        return new ReviewDTO(updated);
    }

    // POST /api/reviews
    @PostMapping
    public ReviewDTO createReview(@RequestBody Review review) {
        Review saved = reviewService.createReview(review);
        return new ReviewDTO(saved);
    }
}
