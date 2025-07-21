USE CookbookWebsite;

-- Category
ALTER TABLE Category
  CHANGE COLUMN categoryId category_id INT AUTO_INCREMENT,
  CHANGE COLUMN categoryName category_name VARCHAR(255) NOT NULL;

-- DifficultyLevels
ALTER TABLE DifficultyLevels
  CHANGE COLUMN difficultyId difficulty_id INT AUTO_INCREMENT,
  CHANGE COLUMN difficultyLevel difficulty_level VARCHAR(50) NOT NULL;

-- Ingredients
ALTER TABLE Ingredients
  CHANGE COLUMN ingredientId ingredient_id INT AUTO_INCREMENT,
  CHANGE COLUMN ingredientName ingredient_name VARCHAR(255) NOT NULL;

-- Roles
ALTER TABLE Roles
  CHANGE COLUMN roleId role_id INT AUTO_INCREMENT,
  CHANGE COLUMN roleName role_name VARCHAR(50) NOT NULL UNIQUE;

-- Users
ALTER TABLE Users
  CHANGE COLUMN userId user_id INT AUTO_INCREMENT,
  CHANGE COLUMN roleId role_id INT NOT NULL,
  CHANGE COLUMN userName user_name VARCHAR(255) NOT NULL,
  CHANGE COLUMN userEmail user_email VARCHAR(255) NOT NULL UNIQUE,
  CHANGE COLUMN passwordHash password_hash VARCHAR(255) NOT NULL;

-- Recipes
ALTER TABLE Recipes
  CHANGE COLUMN recipeId recipe_id INT AUTO_INCREMENT,
  CHANGE COLUMN categoryId category_id INT NOT NULL,
  CHANGE COLUMN difficultyId difficulty_id INT NOT NULL,
  CHANGE COLUMN userId user_id INT NOT NULL,
  CHANGE COLUMN recipeInstructions recipe_instructions TEXT NOT NULL,
  CHANGE COLUMN recipeName recipe_name VARCHAR(255) NOT NULL,
  CHANGE COLUMN recipeImage recipe_image VARCHAR(255),
  CHANGE COLUMN ingredientsNotes ingredients_notes TEXT;

-- UsersFavorites
ALTER TABLE UsersFavorites
  CHANGE COLUMN userId user_id INT NOT NULL,
  CHANGE COLUMN recipeId recipe_id INT NOT NULL;

-- UsersNotes
ALTER TABLE UsersNotes
  CHANGE COLUMN userId user_id INT NOT NULL,
  CHANGE COLUMN recipeId recipe_id INT NOT NULL,
  CHANGE COLUMN noteText note_text TEXT NOT NULL;

-- Reviews
ALTER TABLE Reviews
  CHANGE COLUMN reviewId review_id INT AUTO_INCREMENT,
  CHANGE COLUMN userId user_id INT NOT NULL,
  CHANGE COLUMN recipeId recipe_id INT NOT NULL,
  CHANGE COLUMN score score INT NOT NULL,
  CHANGE COLUMN reviewText review_text TEXT;

-- RecipeIngredients
ALTER TABLE RecipeIngredients
  CHANGE COLUMN recipeId recipe_id INT NOT NULL,
  CHANGE COLUMN ingredientId ingredient_id INT NOT NULL,
  CHANGE COLUMN quantity quantity DECIMAL(10, 2),
  CHANGE COLUMN unit unit VARCHAR(50),
  CHANGE COLUMN isOptional is_optional TINYINT(1) NOT NULL;

-- Tags
ALTER TABLE Tags
  CHANGE COLUMN tagId tag_id INT AUTO_INCREMENT,
  CHANGE COLUMN tagName tag_name VARCHAR(255) NOT NULL;

-- RecipeTags
ALTER TABLE RecipeTags
  CHANGE COLUMN recipeId recipe_id INT NOT NULL,
  CHANGE COLUMN tagId tag_id INT NOT NULL;