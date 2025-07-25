package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.review.ReviewDTO;
import com.cookbookwebsite.model.Review;
import com.cookbookwebsite.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByUserId(Integer userId) {
        return reviewRepository.findByUserUserId(userId)
                .stream()
                .map(ReviewDTO::new)
                .toList();
    }

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
    public Review updateReview(Integer reviewId, Review updatedReview) {
        Review existing = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found."));

        existing.setScore(updatedReview.getScore());
        existing.setReviewText(updatedReview.getReviewText());

        return reviewRepository.save(existing);
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
}
