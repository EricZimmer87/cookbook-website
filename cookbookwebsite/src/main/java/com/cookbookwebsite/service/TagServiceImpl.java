package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;
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

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> getAllTagDTOs() {
        return tagRepository.findAll()
                .stream()
                .map(TagDTO::new)
                .toList();
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
