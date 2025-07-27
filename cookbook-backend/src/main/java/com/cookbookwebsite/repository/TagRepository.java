package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
