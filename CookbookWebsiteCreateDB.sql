-- Create the database
CREATE DATABASE CookbookWebsite;
USE CookbookWebsite;

-- Category table
CREATE TABLE Category (
    CategoryId INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(255) NOT NULL
);

-- DifficultyLevels table
CREATE TABLE DifficultyLevels (
    DifficultyId INT AUTO_INCREMENT PRIMARY KEY,
    DifficultyLevel VARCHAR(50) NOT NULL
);

-- Ingredients table
CREATE TABLE Ingredients (
    IngredientId INT AUTO_INCREMENT PRIMARY KEY,
    IngredientName VARCHAR(255) NOT NULL
);

CREATE TABLE Roles (
    RoleId INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL UNIQUE
);

-- Users table
CREATE TABLE Users (
    UserId INT AUTO_INCREMENT PRIMARY KEY,
    RoleId INT NOT NULL,
    UserName VARCHAR(255) NOT NULL,
    UserEmail VARCHAR(255) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    FOREIGN KEY (RoleId) REFERENCES Roles(RoleId)
);

-- Recipes table
CREATE TABLE Recipes (
    RecipeId INT AUTO_INCREMENT PRIMARY KEY,
    CategoryId INT NOT NULL,
    DifficultyId INT NOT NULL,
    UserId INT NOT NULL,
    RecipeInstructions TEXT NOT NULL,
    RecipeName VARCHAR(255) NOT NULL,
    RecipeImage VARCHAR(255),
    IngredientsNotes TEXT,
    FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId),
    FOREIGN KEY (DifficultyId) REFERENCES DifficultyLevels(DifficultyId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

-- UsersFavorites table
CREATE TABLE UsersFavorites (
    UserId INT NOT NULL,
    RecipeId INT NOT NULL,
    PRIMARY KEY (UserId, RecipeId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId)
);

-- UsersNotes table
CREATE TABLE UsersNotes (
    UserId INT NOT NULL,
    RecipeId INT NOT NULL,
    NoteText TEXT NOT NULL,
    PRIMARY KEY (UserId, RecipeId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId)
);

-- Reviews table
CREATE TABLE Reviews (
    ReviewId INT AUTO_INCREMENT PRIMARY KEY,
    UserId INT NOT NULL,
    RecipeId INT NOT NULL,
    Score INT NOT NULL CHECK (Score BETWEEN 1 AND 5),
    ReviewText TEXT,
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId)
);

-- RecipeIngredients table
CREATE TABLE RecipeIngredients (
    RecipeId INT NOT NULL,
    IngredientId INT NOT NULL,
    Quantity DECIMAL(10, 2),
    Unit VARCHAR(50),
    IsOptional TINYINT(1) NOT NULL,
    PRIMARY KEY (RecipeId, IngredientId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId),
    FOREIGN KEY (IngredientId) REFERENCES Ingredients(IngredientId)
);

-- Tags table
CREATE TABLE Tags (
    TagId INT AUTO_INCREMENT PRIMARY KEY,
    TagName VARCHAR(255) NOT NULL
);  

-- RecipeTags table
CREATE TABLE RecipeTags (
    RecipeId INT NOT NULL,
    TagId INT NOT NULL,
    PRIMARY KEY (RecipeId, TagId),
    FOREIGN KEY (RecipeId) REFERENCES Recipes(RecipeId),
    FOREIGN KEY (TagId) REFERENCES Tags(TagId)
);



-- Insert sample data

-- Insert data into Roles table
INSERT INTO Roles (RoleName)
VALUES ('Admin'), ('Contributor'), ('Viewer');

INSERT INTO Users (UserId, RoleId, UserName, UserEmail, PasswordHash)
VALUES (1, 1, 'Eric Zimmer', 'ericzimmer87@icloud.com', 'dummypassword');

-- Insert data into Category table
INSERT INTO Category (CategoryName)
VALUES ('Entree'), ('Dessert'), ('Condiment'), ('Appetizer'), ('Soup');

-- Insert data into DifficultyLevels table
INSERT INTO DifficultyLevels (DifficultyLevel)
VALUES ('Very Easy'), ('Easy'), ('Medium'), ('Hard'), ('Very Hard');

-- Insert data into Ingredients table
INSERT INTO Ingredients (IngredientName)
VALUES 
('Tofu'),  
('Rolled Oats'), 
('Raw Walnuts'), 
('Basil'), 
('Oregano'), 
('Parsley'), 
('Minced Onion'), 
('Bragg\'s Liquid Aminos'),
('Green Beans'),
('Mushrooms'),
('Onion'),
('Almonds'),
('Soy Milk'),
('Onion Powder'),
('Garlic Powder'),
('Black Pepper'),
('Cayenne Pepper'),
('Nutritional Yeast'),
('Shredded Coconut'),
('Dates'),
('Pumpkin'),
('Raisins'),
('Cinnamon'),
('Ginger'),
('Nutmeg'),
('Arrowroot Powder'),
('Rice Vinegar'),
('Tomato Paste'),
('All Spice'),
('Garbanzo Beans'),
('Carrots'),
('Red Wine Vinegar'),
('Cumin');

-- Insert data into Tags table
INSERT INTO Tags (TagName)
VALUES ('Vegan');

-- Insert data into Recipes table
-- Recipe 1: Tofu Meatballs
INSERT INTO Recipes (CategoryId, DifficultyId, UserId, RecipeInstructions, RecipeName, RecipeImage, IngredientsNotes)
VALUES 
(1, 1, 1, 'Preheat oven to 350° F. Blend oats and walnuts in a blender or food processor. Chopping them up finely on a cutting board could also work. Add blended oats and walnuts to a medium-sized mixing bowl. Add basil, oregano, parsley, minced onion, and Bragg\'s liquid aminos. Mix with fork. Add tofu. Mix with fork or pastry blender until mixed well and tofu is broken up well. Use a flipper scoop to make into balls. Place balls on baking sheet. Optionally, instead of making balls, lay the mixture out on a baking sheet and form into a sheet. Bake at 350° F for 35 minutes.', 
'Tofu Meatballs', NULL, NULL);

-- Recipe 2: Nutty Green Bean Casserole
INSERT INTO Recipes (CategoryId, DifficultyId, UserId, RecipeInstructions, RecipeName, RecipeImage, IngredientsNotes)
VALUES 
(1, 2, 1, 'Preheat oven to 350°F. Slice the mushrooms. Dice the onion. Cook the mushrooms and onion in a pan. Heat the green beans up in a pan. Blend the remaining ingredients in a blender with the liquid from the mushrooms and onion and the green beans. If the mixture in the blender is too thick, add soy milk until it is a creamy consistency. Mix all ingredients together, place in a casserole dish, bake for 15 minutes.', 
'Nutty Green Bean Casserole', NULL, NULL);

-- Recipe 3: Pumpkin Pie, Healthy
INSERT INTO Recipes (CategoryId, DifficultyId, UserId, RecipeInstructions, RecipeName, RecipeImage, IngredientsNotes)
VALUES 
(2, 2, 1, 'Preheat oven to 350°F. For the crust, blend walnuts, coconut, and oats. Then add 4 medjool dates or 8 regular dates and blend until it sticks together. Spread the crust into a pie pan. Blend the remaining ingredients for the filling, using 1/2 cup dates. Put the filling on top of the crust in the pie pan, spreading evenly. Cover with foil. Bake for 45 minutes. Remove foil, bake for 15 additional minutes.', 
'Pumpkin Pie, Healthy', NULL, 'Can use 4 medjool dates or 8 regular dates for the crust.');

-- Recipe 4: Ketchup
INSERT INTO Recipes (CategoryId, DifficultyId, UserId, RecipeInstructions, RecipeName, RecipeImage, IngredientsNotes)
VALUES 
(3, 3, 1, 'Blend all ingredients in a high-powered blender.', 
'Ketchup', NULL, 'If using Medjool Dates, use 7. 12 oz tomato paste is two small (6 oz) cans.');

-- Recipe 5: Eric\'s Bean Burgers
INSERT INTO Recipes (CategoryId, DifficultyId, UserId, RecipeInstructions, RecipeName, RecipeImage, IngredientsNotes)
VALUES 
(1, 2, 1, 'Preheat oven to 350°F. Blend oats and almonds, place in bowl. Blend beans, place in same bowl. Add remaining ingredients. Mix well. Spread on baking sheet or form into burgers. Bake for 25 minutes.', 
'Eric\'s Bean Burgers', NULL, 'Can use one can of garbanzo beans. Can substitute shredded carrots with shredded zucchini.');

-- Insert data into RecipeIngredients table
-- Tofu Meatballs ingredients
INSERT INTO RecipeIngredients (RecipeId, IngredientId, Quantity, Unit, IsOptional)
VALUES 
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Tofu'), 12, 'oz', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Rolled Oats'), 0.5, 'cup', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Raw Walnuts'), 0.5, 'cup', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Basil'), 1, 'tsp', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Oregano'), 1, 'tsp', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Parsley'), 1.5, 'tsp', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Minced Onion'), 0.25, 'cup', 0),
(1, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Bragg\'s Liquid Aminos'), 2, 'tsp', 0);

-- Nutty Green Bean Casserole ingredients
INSERT INTO RecipeIngredients (RecipeId, IngredientId, Quantity, Unit, IsOptional)
VALUES 
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Green Beans'), 2, 'pounds', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Mushrooms'), 8, 'oz', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Onion'), 1, '', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Almonds'), 0.5, 'cup', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Soy Milk'), NULL, '', 1),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Onion Powder'), 1, 'tsp', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Garlic Powder'), 1, 'tsp', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Bragg\'s Liquid Aminos'), 1, 'tsp', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Black Pepper'), 0.125, 'tsp', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Cayenne Pepper'), 0.125, 'tsp', 0),
(2, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Nutritional Yeast'), 0.25, 'cup', 1);

-- Pumpkin Pie, Healthy ingredients
INSERT INTO RecipeIngredients (RecipeId, IngredientId, Quantity, Unit, IsOptional)
VALUES 
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Raw Walnuts'), 0.67, 'cup', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Shredded Coconut'), 0.67, 'cup', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Rolled Oats'), 0.67, 'cup', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Dates'), 1, 'cup', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Pumpkin'), 15, 'oz', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Raisins'), 0.5, 'cup', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Cinnamon'), 1, 'tsp', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Ginger'), 0.5, 'tsp', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Nutmeg'), 0.5, 'tsp', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Arrowroot Powder'), 2.5, 'tbsp', 0),
(3, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Tofu'), 10, 'oz', 0);

-- Ketchup ingredients
INSERT INTO RecipeIngredients (RecipeId, IngredientId, Quantity, Unit, IsOptional)
VALUES 
(4, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Dates'), 14, '', 0),
(4, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Tomato Paste'), 12, 'oz', 0),
(4, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Rice Vinegar'), 2, 'tbsp', 0),
(4, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'All Spice'), 0.5, 'tsp', 0);

-- Eric's Bean Burgers ingredients
INSERT INTO RecipeIngredients (RecipeId, IngredientId, Quantity, Unit, IsOptional)
VALUES 
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Rolled Oats'), 0.5, 'cup', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Almonds'), 0.5, 'cup', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Garbanzo Beans'), 1, 'can', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Carrots'), 1, 'medium', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Cumin'), 1, 'tsp', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Onion Powder'), 0.5, 'tsp', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Garlic Powder'), 0.5, 'tsp', 0),
(5, (SELECT IngredientId FROM Ingredients WHERE IngredientName = 'Cayenne Pepper'), 0.25, 'tsp', 1);
