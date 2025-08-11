-- Create the database
CREATE DATABASE cookbook_website;
USE cookbook_website;

-- category table
CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

-- difficulty_levels table
CREATE TABLE difficulty_levels (
    difficulty_id INT AUTO_INCREMENT PRIMARY KEY,
    difficulty_level VARCHAR(50) NOT NULL
);

-- ingredients table
CREATE TABLE ingredients (
    ingredient_id INT AUTO_INCREMENT PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL
);

-- roles table
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    is_banned tinyint(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- recipes table
CREATE TABLE recipes (
    recipe_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    difficulty_id INT NOT NULL,
    user_id INT NOT NULL,
    recipe_instructions TEXT NOT NULL,
    recipe_name VARCHAR(255) NOT NULL,
    recipe_image VARCHAR(255),
    ingredients_notes TEXT,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (difficulty_id) REFERENCES difficulty_levels(difficulty_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- users_favorites table
CREATE TABLE users_favorites (
    user_id INT NOT NULL,
    recipe_id INT NOT NULL,
    PRIMARY KEY (user_id, recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

-- users_notes table
CREATE TABLE users_notes (
    user_id INT NOT NULL,
    recipe_id INT NOT NULL,
    note_text TEXT NOT NULL,
    PRIMARY KEY (user_id, recipe_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

-- reviews table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    recipe_id INT NOT NULL,
    score INT NOT NULL CHECK (score BETWEEN 1 AND 5),
    review_text TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
);

-- recipe_ingredients table
CREATE TABLE recipe_ingredients (
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity DECIMAL(10, 2),
    unit VARCHAR(50),
    is_optional TINYINT(1) NOT NULL,
    PRIMARY KEY (recipe_id, ingredient_id),
    CONSTRAINT fk_ri_recipe
        FOREIGN KEY (recipe_id)
        REFERENCES recipes(recipe_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_ri_ingredient
        FOREIGN KEY (ingredient_id)
        REFERENCES ingredients(ingredient_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- tags table
CREATE TABLE tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255) NOT NULL
);

-- recipe_tags table
CREATE TABLE recipe_tags (
    recipe_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (recipe_id, tag_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id)
);

-- password_reset_tokens table
CREATE TABLE password_reset_tokens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(255) NOT NULL,
  user_id BIGINT NOT NULL,
  expiry TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- insert sample data
-- insert data into roles table
INSERT INTO roles (role_name)
VALUES ('admin'), ('contributor'), ('viewer');

INSERT INTO users (user_id, role_id, user_name, user_email, password_hash)
VALUES (1, 1, 'eric zimmer', 'ericzimmer87@icloud.com', 'dummypassword');

-- insert data into category table
INSERT INTO category (category_name)
VALUES ('entree'), ('dessert'), ('condiment'), ('appetizer'), ('soup');

-- insert data into difficulty_levels table
INSERT INTO difficulty_levels (difficulty_level)
VALUES ('very easy'), ('easy'), ('medium'), ('hard'), ('very hard');

-- insert data into ingredients table
INSERT INTO ingredients (ingredient_name)
VALUES
('tofu'),
('rolled oats'),
('raw walnuts'),
('basil'),
('oregano'),
('parsley'),
('minced onion'),
('bragg''s liquid aminos'),
('green beans'),
('mushrooms'),
('onion'),
('almonds'),
('soy milk'),
('onion powder'),
('garlic powder'),
('black pepper'),
('cayenne pepper'),
('nutritional yeast'),
('shredded coconut'),
('dates'),
('pumpkin'),
('raisins'),
('cinnamon'),
('ginger'),
('nutmeg'),
('arrowroot powder'),
('rice vinegar'),
('tomato paste'),
('all spice'),
('garbanzo beans'),
('carrots'),
('red wine vinegar'),
('cumin');

-- insert data into tags table
INSERT INTO tags (tag_name) VALUES
  ('Vegan'),
  ('Vegetarian'),
  ('Gluten-Free'),
  ('Dairy-Free'),
  ('Low-Carb'),
  ('High-Protein'),
  ('Keto'),
  ('Paleo'),
  ('Whole30'),
  ('Quick'),
  ('Easy'),
  ('5 Ingredients or Less'),
  ('One-Pot'),
  ('Sheet Pan'),
  ('No-Bake'),
  ('Slow Cooker'),
  ('Instant Pot'),
  ('Air Fryer'),
  ('Grilled'),
  ('Baked'),
  ('Fried'),
  ('Raw'),
  ('Snack'),
  ('Dessert'),
  ('Appetizer'),
  ('Side Dish'),
  ('Main Course'),
  ('Soup'),
  ('Salad'),
  ('Pasta'),
  ('Pizza'),
  ('Sandwich'),
  ('Wrap'),
  ('Burger'),
  ('Rice'),
  ('Noodles'),
  ('Seafood'),
  ('Chicken'),
  ('Beef'),
  ('Pork'),
  ('Tofu'),
  ('Beans'),
  ('Chickpeas'),
  ('Lentils'),
  ('Eggs'),
  ('Chocolate'),
  ('Spicy'),
  ('Sweet'),
  ('Savory'),
  ('Comfort Food'),
  ('Healthy'),
  ('Kid-Friendly'),
  ('Holiday'),
  ('Christmas'),
  ('Thanksgiving'),
  ('Easter'),
  ('Halloween'),
  ('Asian'),
  ('Mexican'),
  ('Italian'),
  ('Indian'),
  ('Mediterranean'),
  ('Middle Eastern'),
  ('American'),
  ('French'),
  ('Thai'),
  ('Japanese'),
  ('Korean');

-- insert data into recipes table
-- recipe 1: tofu meatballs
INSERT INTO recipes (category_id, difficulty_id, user_id, recipe_instructions, recipe_name, recipe_image, ingredients_notes)
VALUES
(1, 1, 1, 'Preheat oven to 350°F. Blend oats and walnuts in a blender or food processor. Chopping them up finely on a cutting board could also work. Add blended oats and walnuts to a medium-sized mixing bowl. Add basil, oregano, parsley, minced onion, and bragg''s liquid aminos. Mix with fork. Add tofu. Mix with fork or pastry blender until mixed well and tofu is broken up well. Use a flipper scoop to make into balls. Place balls on baking sheet. Optionally, instead of making balls, lay the mixture out on a baking sheet and form into a sheet. Bake at 350°F for 35 minutes.',
'Tofu Meatballs', null, null);

-- recipe 2: nutty green bean casserole
INSERT INTO recipes (category_id, difficulty_id, user_id, recipe_instructions, recipe_name, recipe_image, ingredients_notes)
VALUES
(1, 2, 1, 'Preheat oven to 350°F. slice the mushrooms. Dice the onion. Cook the mushrooms and onion in a pan. Heat the green beans up in a pan. Blend the remaining ingredients in a blender with the liquid from the mushrooms and onion and the green beans. If the mixture in the blender is too thick, add soy milk until it is a creamy consistency. Mix all ingredients together, place in a casserole dish, bake for 15 minutes.',
'Nutty Green Bean Casserole', null, null);

-- recipe 3: pumpkin pie, healthy
INSERT INTO recipes (category_id, difficulty_id, user_id, recipe_instructions, recipe_name, recipe_image, ingredients_notes)
VALUES
(2, 2, 1, 'Preheat oven to 350°F. For the crust, blend walnuts, coconut, and oats. Then add 4 medjool dates or 8 regular dates and blend until it sticks together. Spread the crust into a pie pan. blend the remaining ingredients for the filling, using 1/2 cup dates. Put the filling on top of the crust in the pie pan, spreading evenly. cover with foil. Bake for 45 minutes. Remove foil, bake for 15 additional minutes.',
'Pumpkin Pie, Healthy', null, 'Can use 4 medjool dates or 8 regular dates for the crust.');

-- recipe 4: ketchup
INSERT INTO recipes (category_id, difficulty_id, user_id, recipe_instructions, recipe_name, recipe_image, ingredients_notes)
VALUES
(3, 3, 1, 'Blend all ingredients in a high-powered blender.',
'Ketchup', null, 'If using medjool dates, use 7. 12 oz tomato paste is two small (6 oz) cans.');

-- recipe 5: eric's bean burgers
INSERT INTO recipes (category_id, difficulty_id, user_id, recipe_instructions, recipe_name, recipe_image, ingredients_notes)
VALUES
(1, 2, 1, 'Preheat oven to 350°F. Blend oats and almonds, place in bowl. blend beans, place in same bowl. Add remaining ingredients. Mix well. spread on baking sheet or form into burgers. Bake for 25 minutes.',
'Eric''s Bean Burgers', null, 'Can use one can of garbanzo beans. Can substitute shredded carrots with shredded zucchini.');
