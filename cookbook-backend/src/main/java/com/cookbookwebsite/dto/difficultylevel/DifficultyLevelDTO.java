package com.cookbookwebsite.dto.difficultylevel;

import com.cookbookwebsite.model.DifficultyLevel;

public class DifficultyLevelDTO {
    private Integer difficultyLevelId;
    private String difficultyLevelName;

    public DifficultyLevelDTO(DifficultyLevel difficultyLevel) {
        this.difficultyLevelId = difficultyLevel.getDifficultyId();
        this.difficultyLevelName = difficultyLevel.getDifficultyLevelName();
    }

    // Getters
    public Integer getDifficultyLevelId() { return difficultyLevelId; }
    public String getDifficultyLevelName() { return difficultyLevelName; }
}
