package com.cookbookwebsite.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "difficulty_levels")
public class DifficultyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficulty_id")
    private Integer difficultyId;

    @OneToMany(mappedBy = "difficultyLevel")
    private List<Recipe> recipes;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    // Getters and Setters
    public Integer getDifficultyId() { return difficultyId; }
    public void setDifficultyId(Integer difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<Recipe> getRecipes() { return recipes; }
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
