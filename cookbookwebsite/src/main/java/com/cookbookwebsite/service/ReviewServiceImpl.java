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

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
}
