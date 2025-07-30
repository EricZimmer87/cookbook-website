package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.review.ReviewCreateDTO;
import com.cookbookwebsite.dto.review.ReviewDTO;
import com.cookbookwebsite.model.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByUserId(Integer userId);
    List<ReviewDTO> getReviewsByRecipeId(Integer recipeId);
    Review createReview(ReviewCreateDTO dto);
    ReviewDTO getReviewById(Integer reviewId);
    Review updateReview(Integer reviewId, Review updatedReview);
    void deleteReviewById(Integer reviewId);
}
