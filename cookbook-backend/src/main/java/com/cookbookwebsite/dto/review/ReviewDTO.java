package com.cookbookwebsite.dto.review;

import com.cookbookwebsite.model.Review;

public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private String userName;
    private Integer recipeId;
    private String recipeName;
    private Integer score;
    private String reviewText;

    public ReviewDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.userId = review.getUser().getUserId();
        this.userName = review.getUser().getUserName();
        this.recipeId = review.getRecipe().getRecipeId();
        this.recipeName = review.getRecipe().getRecipeName();
        this.score = review.getScore();
        this.reviewText = review.getReviewText();
    }

    // Getters
    public Integer getReviewId() { return reviewId; }
    public Integer getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Integer getRecipeId() { return recipeId; }
    public String getRecipeName() { return recipeName; }
    public Integer getScore() { return score; }
    public String getReviewText() { return reviewText; }
}
