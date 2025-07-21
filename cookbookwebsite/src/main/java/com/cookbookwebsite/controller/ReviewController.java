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

    // GET /api/reviews/user/userId
    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable Integer userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    // GET /api/reviews/recipe/recipeId
    @GetMapping("/recipe/{recipeId}")
    public List<ReviewDTO> getReviewsByRecipeId(@PathVariable Integer recipeId) {
        return reviewService.getReviewsByRecipeId(recipeId);
    }

    // POST /api/reviews
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
}
