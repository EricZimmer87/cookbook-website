package com.cookbookwebsite.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = @UniqueConstraint(name = "ux_reviews_user_recipe",
                columnNames = {"user_id", "recipe_id"})
)public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "review_text")
    private String reviewText;

    // Constructors
    public Review() {}

    public Review(User user, Recipe recipe, Integer score, String reviewText) {
        this.user = user;
        this.recipe = recipe;
        this.score = score;
        this.reviewText = reviewText;
    }

    // Getters and Setters
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getScore() { return score; }
    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
