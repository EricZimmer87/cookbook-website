package com.cookbookwebsite.dto.review;

public class ReviewCreateDTO {
    private Integer recipeId;
    private Integer score;
    private String reviewText;

    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}


