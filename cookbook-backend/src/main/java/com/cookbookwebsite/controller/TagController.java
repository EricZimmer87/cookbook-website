package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;
import com.cookbookwebsite.service.TagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Get all tags
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @GetMapping
    public List<TagDTO> getAllTagDTOs() {
        return tagService.getAllTagDTOs();
    }

    // Get tag by ID
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @GetMapping("/{tagId}")
    public TagDTO getTagById(@PathVariable Integer tagId) {
        return tagService.getTagById(tagId);
    }

    // Update tag
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable Integer id, @RequestBody Tag updatedTag) {
        Tag tag = tagService.getTagEntityById(id);

        if (updatedTag.getTagName() != null) {
            tag.setTagName(updatedTag.getTagName());
        }

        Tag savedTag = tagService.saveTag(tag);
        return new TagDTO(savedTag);
    }

    // Create tag
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    // Delete tag
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable Integer tagId) {
        tagService.deleteTagById(tagId);
    }
}
