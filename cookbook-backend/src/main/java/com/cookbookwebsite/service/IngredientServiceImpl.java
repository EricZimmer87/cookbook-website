package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.ingredient.IngredientDTO;
import com.cookbookwebsite.exception.DuplicateResourceException;
import com.cookbookwebsite.exception.ResourceInUseException;
import com.cookbookwebsite.model.Ingredient;
import com.cookbookwebsite.repository.IngredientRepository;
import com.cookbookwebsite.repository.RecipeIngredientRepository;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final EntityManager em;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeIngredientRepository recipeIngredientRepository,
                                 EntityManager em) {
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredientDTOs() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDTO getIngredientById(Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + ingredientId));
        return new IngredientDTO(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient getIngredientEntityById(Integer ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + ingredientId));
    }

    @Override
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public Ingredient createIngredient(Ingredient ingredient) {
        var name = ingredient.getIngredientName() == null ? null : ingredient.getIngredientName().trim();
        ingredient.setIngredientName(name);
        try {
            var saved = ingredientRepository.save(ingredient);
            em.flush(); // force the constraint check now (not at tx commit)
            return saved;
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            String pretty = "Ingredient '" + name + "' already exists.";
            Throwable cause = org.springframework.core.NestedExceptionUtils.getMostSpecificCause(e);

            // If Hibernate gives us the constraint name, prefer that:
            if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
                var constraint = cve.getConstraintName();
                if ("ux_ingredients_name".equalsIgnoreCase(constraint)) {
                    throw new DuplicateResourceException(pretty);
                }
            }

            // Fallback: check MySQL error signature
            if (cause instanceof java.sql.SQLIntegrityConstraintViolationException sql) {
                if ("23000".equals(sql.getSQLState()) || sql.getErrorCode() == 1062) {
                    throw new DuplicateResourceException(pretty);
                }
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteIngredientById(Integer ingredientId) {
        // App-level guard — avoids hitting FK error and lets us return a friendly message
        if (recipeIngredientRepository.existsByIngredient_IngredientId(ingredientId)) {
            throw new ResourceInUseException("Cannot delete ingredient: it is used by one or more recipes.");
        }

        try {
            ingredientRepository.deleteById(ingredientId);
        } catch (DataIntegrityViolationException ex) {
            // Safety net in case of race conditions / FK constraints
            throw new ResourceInUseException("Cannot delete ingredient: it is used by one or more recipes.");
        }
    }
}