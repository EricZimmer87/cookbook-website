package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.DifficultyLevel;
import com.cookbookwebsite.service.DifficultyLevelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/difficulty-levels")
public class DifficultyLevelController {
    private final DifficultyLevelService difficultyLevelService;

    public DifficultyLevelController(DifficultyLevelService difficultyLevelService) {
        this.difficultyLevelService = difficultyLevelService;
    }

    // Get all difficulty levels
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @GetMapping
    public List<DifficultyLevelDTO> getAllDifficultyLevelDTOs() {
        return difficultyLevelService.getAllDifficultyLevelDTOs();
    }

    // Get difficulty level by ID
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @GetMapping("/{difficultyLevelId}")
    public DifficultyLevelDTO getDifficultyLevelById(@PathVariable Integer difficultyLevelId) {
        return difficultyLevelService.getDifficultyLevelById(difficultyLevelId);
    }

    // Update difficulty level
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public DifficultyLevelDTO updateDifficultyLevel(@PathVariable Integer id, @RequestBody DifficultyLevel updatedDifficultyLevel) {
        DifficultyLevel difficultyLevel = difficultyLevelService.getDifficultyLevelEntityById(id);

        if (updatedDifficultyLevel.getDifficultyLevelName() != null) {
            difficultyLevel.setDifficultyLevelName(updatedDifficultyLevel.getDifficultyLevelName());
        }

        DifficultyLevel savedDifficultyLevel = difficultyLevelService.saveDifficultyLevel(difficultyLevel);
        return new DifficultyLevelDTO(savedDifficultyLevel);
    }

    // Create new difficulty level
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DifficultyLevel createDifficultyLevel(@RequestBody DifficultyLevel difficultyLevel) {
        return difficultyLevelService.createDifficultyLevel(difficultyLevel);
    }

    // Delete difficulty level
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{difficultyLevelId}")
    public void deleteDifficultyLevel(@PathVariable Integer difficultyLevelId) {
        difficultyLevelService.deleteDifficultyLevelById(difficultyLevelId);
    }
}
