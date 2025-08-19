package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.recipe.IngredientRow;
import com.cookbookwebsite.dto.recipe.RecipeCreateRequest;
import com.cookbookwebsite.dto.recipe.RecipeDTO;
import com.cookbookwebsite.dto.recipe.RecipeUpdateRequest;
import com.cookbookwebsite.model.*;
import com.cookbookwebsite.repository.*;
import com.cookbookwebsite.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;
    private final ReviewRepository reviewRepository;
    private final UserNoteRepository userNoteRepository;
    private final UserFavoriteRepository userFavoriteRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             UserRepository userRepository,
                             CategoryRepository categoryRepository,
                             DifficultyLevelRepository difficultyLevelRepository,
                             IngredientRepository ingredientRepository,
                             RecipeIngredientRepository recipeIngredientRepository, // ← comma, not semicolon
                             TagRepository tagRepository,
                             RecipeTagRepository recipeTagRepository,
                             ReviewRepository reviewRepository,
                             UserNoteRepository userNoteRepository,
                             UserFavoriteRepository userFavoriteRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.tagRepository = tagRepository;
        this.recipeTagRepository = recipeTagRepository;
        this.reviewRepository = reviewRepository;
        this.userNoteRepository = userNoteRepository;
        this.userFavoriteRepository = userFavoriteRepository;
    }

    private BigDecimal parseQuantity(String input) {
        if (input == null || input.isBlank()) return null;
        // normalize
        String s = input.trim()
                .replace("−","-")
                .replace("½","1/2").replace("⅓","1/3").replace("⅔","2/3")
                .replace("¼","1/4").replace("¾","3/4")
                .replace("⅕","1/5").replace("⅖","2/5").replace("⅗","3/5").replace("⅘","4/5")
                .replace("⅙","1/6").replace("⅚","5/6")
                .replace("⅛","1/8").replace("⅜","3/8").replace("⅝","5/8").replace("⅞","7/8")
                .replaceAll("(\\d)\\s*-\\s*(\\d)", "$1 $2"); // 3-1/2 -> 3 1/2

        // plain number (e.g., 0.5)
        if (s.matches("^[+-]?\\d+(\\.\\d+)?$")) return new BigDecimal(s).setScale(2, RoundingMode.HALF_UP);

        // mixed number "a b/c"
        var m1 = s.trim().split("\\s+");
        if (m1.length == 2 && m1[0].matches("[+-]?\\d+") && m1[1].matches("\\d+/\\d+")) {
            String[] f = m1[1].split("/");
            return new BigDecimal(m1[0])
                    .add(new BigDecimal(f[0]).divide(new BigDecimal(f[1]), 6, RoundingMode.HALF_UP))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // simple fraction "b/c"
        if (s.matches("^[+-]?\\d+\\s*/\\s*\\d+$")) {
            String[] f = s.replace(" ","").split("/");
            return new BigDecimal(f[0]).divide(new BigDecimal(f[1]), 6, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        throw new IllegalArgumentException("Invalid quantity: " + input);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getAllRecipeDTOs() {
        return recipeRepository.findAll().stream().map(RecipeDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeById(Integer id) {
        var recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));
        return new RecipeDTO(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getRecipesByUserId(Integer userId) {
        return recipeRepository.findByUserUserId(userId).stream().map(RecipeDTO::new).toList();
    }

    @Override
    @Transactional
    public Recipe createRecipeForCurrentUser(RecipeCreateRequest req) {
        // Basic validation
        if (req.recipeName() == null || req.recipeName().isBlank())
            throw new IllegalStateException("Recipe name is required");
        if (req.recipeInstructions() == null || req.recipeInstructions().isBlank())
            throw new IllegalStateException("Instructions are required");

        // Current user
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails me)) {
            throw new RuntimeException("Not authenticated");
        }
        var user = userRepository.findById(me.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Base recipe
        var recipe = new Recipe();
        recipe.setUser(user);
        recipe.setRecipeName(req.recipeName().trim());
        recipe.setRecipeInstructions(req.recipeInstructions());

        if (req.categoryId() == null)  throw new IllegalStateException("Category is required");
        if (req.difficultyId() == null) throw new IllegalStateException("Difficulty is required");

        var cat = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + req.categoryId()));
        recipe.setCategory(cat);

        var diff = difficultyLevelRepository.findById(req.difficultyId())
                .orElseThrow(() -> new RuntimeException("Difficulty not found: " + req.difficultyId()));
        recipe.setDifficultyLevel(diff);

        // Persist first to get recipeId for composite keys
        final Recipe savedRecipe = recipeRepository.save(recipe);

        // Ingredients
        if (req.ingredients() != null && !req.ingredients().isEmpty()) {
            var seen = new java.util.HashSet<Integer>();
            var rows = req.ingredients().stream()
                    .filter(r -> r != null && r.ingredientId() != null && seen.add(r.ingredientId()))
                    .toList();

            var ingredientIds = rows.stream().map(IngredientRow::ingredientId).toList();
            var ingredients = ingredientRepository.findAllById(ingredientIds);

            if (ingredients.size() != ingredientIds.size()) {
                var foundIds = ingredients.stream().map(Ingredient::getIngredientId)
                        .collect(java.util.stream.Collectors.toSet());
                var missing = ingredientIds.stream().filter(id -> !foundIds.contains(id)).toList();
                throw new RuntimeException("Ingredient(s) not found: " + missing);
            }

            var ingMap = ingredients.stream()
                    .collect(java.util.stream.Collectors.toMap(Ingredient::getIngredientId, i -> i));

            var toSave = rows.stream().map(r -> new RecipeIngredient(
                    savedRecipe,
                    ingMap.get(r.ingredientId()),
                    r.quantity() == null ? null : parseQuantity(r.quantity()),
                    (r.unit() == null ? null : r.unit().trim()),
                    r.isOptional() != null ? r.isOptional() : Boolean.FALSE
            )).toList();

            recipeIngredientRepository.saveAll(toSave);
        }

        // Tags (optional)
        if (req.tagIds() != null && !req.tagIds().isEmpty()) {
            var tagIds = req.tagIds().stream().distinct().toList();
            var tags = tagRepository.findAllById(tagIds);

            if (tags.size() != tagIds.size()) {
                var found = tags.stream().map(Tag::getTagId)
                        .collect(java.util.stream.Collectors.toSet());
                var missing = tagIds.stream().filter(id -> !found.contains(id)).toList();
                throw new RuntimeException("Tag(s) not found: " + missing);
            }

            var tagLinks = tags.stream()
                    .map(tag -> new RecipeTag(savedRecipe, tag))
                    .toList();

            recipeTagRepository.saveAll(tagLinks);
        }

        return savedRecipe;
    }

    // service/RecipeServiceImpl.java
    @Override
    @Transactional
    public RecipeDTO updateRecipeForCurrentUser(Integer recipeId, RecipeUpdateRequest req) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var me = (CustomUserDetails) auth.getPrincipal();
        var recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        // Authorization: owner or admin
        boolean isOwner = recipe.getUser().getUserId().equals(me.getUserId());
        boolean isAdmin = me.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isOwner && !isAdmin) {
            throw new org.springframework.security.access.AccessDeniedException("Forbidden");
        }

        // Validate required fields (since DB schema requires them)
        if (req.recipeName() == null || req.recipeName().isBlank())
            throw new IllegalStateException("Recipe name is required");
        if (req.recipeInstructions() == null || req.recipeInstructions().isBlank())
            throw new IllegalStateException("Instructions are required");
        if (req.categoryId() == null) throw new IllegalStateException("Category is required");
        if (req.difficultyId() == null) throw new IllegalStateException("Difficulty is required");

        // Update base fields
        recipe.setRecipeName(req.recipeName().trim());
        recipe.setRecipeInstructions(req.recipeInstructions());

        var category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + req.categoryId()));
        recipe.setCategory(category);

        var diff = difficultyLevelRepository.findById(req.difficultyId())
                .orElseThrow(() -> new RuntimeException("Difficulty not found: " + req.difficultyId()));
        recipe.setDifficultyLevel(diff);

        recipe = recipeRepository.save(recipe);

        // Replace ingredients
        recipeIngredientRepository.deleteByRecipe_RecipeId(recipeId);
        if (req.ingredients() != null && !req.ingredients().isEmpty()) {
            var seen = new java.util.HashSet<Integer>();
            var rows = req.ingredients().stream()
                    .filter(r -> r != null && r.ingredientId() != null && seen.add(r.ingredientId()))
                    .toList();

            var ingIds = rows.stream().map(IngredientRow::ingredientId).toList();
            var ings = ingredientRepository.findAllById(ingIds);
            if (ings.size() != ingIds.size()) {
                var found = ings.stream().map(Ingredient::getIngredientId).collect(java.util.stream.Collectors.toSet());
                var missing = ingIds.stream().filter(id -> !found.contains(id)).toList();
                throw new RuntimeException("Ingredient(s) not found: " + missing);
            }
            var ingMap = ings.stream().collect(java.util.stream.Collectors.toMap(Ingredient::getIngredientId, i -> i));

            var riList = new java.util.ArrayList<RecipeIngredient>();
            for (var r : rows) {
                var ing = ingMap.get(r.ingredientId());
                var ri = new RecipeIngredient(
                        recipe,
                        ing,
                        r.quantity() == null ? null : parseQuantity(r.quantity()),
                        r.unit() == null ? null : r.unit().trim(),
                        r.isOptional() != null ? r.isOptional() : Boolean.FALSE
                );
                riList.add(ri);
            }
            recipeIngredientRepository.saveAll(riList);
        }

        // Replace tags
        recipeTagRepository.deleteByRecipe_RecipeId(recipeId);
        if (req.tagIds() != null && !req.tagIds().isEmpty()) {
            var tagIds = req.tagIds().stream().distinct().toList();
            var tags = tagRepository.findAllById(tagIds);
            if (tags.size() != tagIds.size()) {
                var found = tags.stream().map(Tag::getTagId).collect(java.util.stream.Collectors.toSet());
                var missing = tagIds.stream().filter(id -> !found.contains(id)).toList();
                throw new RuntimeException("Tag(s) not found: " + missing);
            }
            Recipe finalRecipe = recipe;
            var rtList = tags.stream().map(t -> new RecipeTag(finalRecipe, t)).toList();
            recipeTagRepository.saveAll(rtList);
        }

        // Return updated view
        return new RecipeDTO(recipe);
    }

    @Transactional
    public void deleteRecipeForCurrentUser(Integer recipeId) {
        var recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails me)) {
            throw new RuntimeException("Not authenticated");
        }
        boolean isOwner = recipe.getUser().getUserId().equals(me.getUserId());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            // Spring will turn this into 403 automatically
            throw new org.springframework.security.access.AccessDeniedException("Forbidden");
        }

        // Delete dependents explicitly
        recipeIngredientRepository.deleteByRecipe_RecipeId(recipeId);
        recipeTagRepository.deleteByRecipe_RecipeId(recipeId);
        reviewRepository.deleteByRecipe_RecipeId(recipeId);
        userNoteRepository.deleteByRecipe_RecipeId(recipeId);
        userFavoriteRepository.deleteByRecipe_RecipeId(recipeId);

        recipeRepository.delete(recipe);
    }
}