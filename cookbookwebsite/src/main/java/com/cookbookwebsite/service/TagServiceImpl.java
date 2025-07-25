package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Get all tag DTOs
    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> getAllTagDTOs() {
        return tagRepository.findAll()
                .stream()
                .map(TagDTO::new)
                .toList();
    }

    // Get tagDTO by tag ID
    @Override
    @Transactional(readOnly = true)
    public TagDTO getTagById(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with ID: " + tagId));
        return new TagDTO(tag);
    }

    // Get tag entity by tag ID
    @Override
    @Transactional(readOnly = true)
    public Tag getTagEntityById(Integer id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    // Update tag
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Create tag
    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Delete tag
    @Override
    @Transactional
    public void deleteTagById(Integer tagId) {
        tagRepository.deleteById(tagId);
    }
}
