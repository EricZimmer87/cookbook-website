CREATE DATABASE  IF NOT EXISTS `cookbook_website` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cookbook_website`;
-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: localhost    Database: cookbook_website
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'entree'),(2,'dessert'),(3,'condiment'),(4,'appetizer'),(5,'soup');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `difficulty_levels`
--

DROP TABLE IF EXISTS `difficulty_levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `difficulty_levels` (
  `difficulty_id` int NOT NULL AUTO_INCREMENT,
  `difficulty_level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`difficulty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `difficulty_levels`
--

LOCK TABLES `difficulty_levels` WRITE;
/*!40000 ALTER TABLE `difficulty_levels` DISABLE KEYS */;
INSERT INTO `difficulty_levels` VALUES (1,'very easy'),(2,'easy'),(3,'medium'),(4,'hard'),(5,'very hard');
/*!40000 ALTER TABLE `difficulty_levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `ingredient_id` int NOT NULL AUTO_INCREMENT,
  `ingredient_name` varchar(255) NOT NULL,
  PRIMARY KEY (`ingredient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (1,'tofu'),(2,'rolled oats'),(3,'raw walnuts'),(4,'basil'),(5,'oregano'),(6,'parsley'),(7,'minced onion'),(8,'bragg\'s liquid aminos'),(9,'green beans'),(10,'mushrooms'),(11,'onion'),(12,'almonds'),(13,'soy milk'),(14,'onion powder'),(15,'garlic powder'),(16,'black pepper'),(17,'cayenne pepper'),(18,'nutritional yeast'),(19,'shredded coconut'),(20,'dates'),(21,'pumpkin'),(22,'raisins'),(23,'cinnamon'),(24,'ginger'),(25,'nutmeg'),(26,'arrowroot powder'),(27,'rice vinegar'),(28,'tomato paste'),(29,'all spice'),(30,'garbanzo beans'),(31,'carrots'),(32,'red wine vinegar'),(33,'cumin');
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_ingredients`
--

DROP TABLE IF EXISTS `recipe_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_ingredients` (
  `recipe_id` int NOT NULL,
  `ingredient_id` int NOT NULL,
  `quantity` double DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `is_optional` tinyint(1) NOT NULL,
  PRIMARY KEY (`recipe_id`,`ingredient_id`),
  KEY `ingredient_id` (`ingredient_id`),
  CONSTRAINT `recipe_ingredients_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`),
  CONSTRAINT `recipe_ingredients_ibfk_2` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_ingredients`
--

LOCK TABLES `recipe_ingredients` WRITE;
/*!40000 ALTER TABLE `recipe_ingredients` DISABLE KEYS */;
INSERT INTO `recipe_ingredients` VALUES (1,1,1,'block',0),(1,2,1.5,'cups',0),(1,3,0.75,'cups',0),(1,4,2,'tsp',0),(1,5,2,'tsp',0),(1,6,1,'tsp',0),(1,7,1,'tbsp',0),(1,8,2,'tbsp',0),(2,9,3,'cups',0),(2,10,1.5,'cups',0),(2,11,1,'medium',0),(2,12,0.5,'cups',0),(2,13,1,'cups',0),(2,14,1,'tsp',0),(2,15,1,'tsp',0),(3,2,1,'cup',0),(3,3,1.5,'cups',0),(3,19,0.5,'cups',0),(3,20,0.5,'cups',0),(3,21,1,'can',0),(3,22,1,'cup',0),(3,23,1.5,'tsp',0),(3,24,0.5,'tsp',0),(3,25,0.5,'tsp',0),(3,26,1,'tbsp',1),(4,14,1,'tsp',0),(4,15,1,'tsp',0),(4,20,7,'pieces',0),(4,27,2,'tbsp',0),(4,28,12,'oz',0),(4,29,0.5,'tsp',0),(5,2,1,'cup',0),(5,4,1,'tsp',0),(5,5,1,'tsp',0),(5,6,1,'tsp',0),(5,12,0.5,'cups',0),(5,15,1,'tsp',0),(5,16,0.25,'tsp',0),(5,17,0.25,'tsp',1),(5,30,1.5,'cups',0),(5,31,1,'cup',1);
/*!40000 ALTER TABLE `recipe_ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_tags`
--

DROP TABLE IF EXISTS `recipe_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_tags` (
  `recipe_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`recipe_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `recipe_tags_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`),
  CONSTRAINT `recipe_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_tags`
--

LOCK TABLES `recipe_tags` WRITE;
/*!40000 ALTER TABLE `recipe_tags` DISABLE KEYS */;
INSERT INTO `recipe_tags` VALUES (1,1);
/*!40000 ALTER TABLE `recipe_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `recipe_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `difficulty_id` int NOT NULL,
  `user_id` int NOT NULL,
  `recipe_instructions` text NOT NULL,
  `recipe_name` varchar(255) NOT NULL,
  `recipe_image` varchar(255) DEFAULT NULL,
  `ingredients_notes` text,
  PRIMARY KEY (`recipe_id`),
  KEY `category_id` (`category_id`),
  KEY `difficulty_id` (`difficulty_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `recipes_ibfk_2` FOREIGN KEY (`difficulty_id`) REFERENCES `difficulty_levels` (`difficulty_id`),
  CONSTRAINT `recipes_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (1,1,1,1,'Preheat oven to 350°F. Blend oats and walnuts in a blender or food processor. Chopping them up finely on a cutting board could also work. Add blended oats and walnuts to a medium-sized mixing bowl. Add basil, oregano, parsley, minced onion, and bragg\'s liquid aminos. Mix with fork. Add tofu. Mix with fork or pastry blender until mixed well and tofu is broken up well. Use a flipper scoop to make into balls. Place balls on baking sheet. Optionally, instead of making balls, lay the mixture out on a baking sheet and form into a sheet. Bake at 350°F for 35 minutes.','Tofu Meatballs',NULL,NULL),(2,1,2,1,'Preheat oven to 350°F. slice the mushrooms. Dice the onion. Cook the mushrooms and onion in a pan. Heat the green beans up in a pan. Blend the remaining ingredients in a blender with the liquid from the mushrooms and onion and the green beans. If the mixture in the blender is too thick, add soy milk until it is a creamy consistency. Mix all ingredients together, place in a casserole dish, bake for 15 minutes.','Nutty Green Bean Casserole',NULL,NULL),(3,2,2,1,'Preheat oven to 350°F. For the crust, blend walnuts, coconut, and oats. Then add 4 medjool dates or 8 regular dates and blend until it sticks together. Spread the crust into a pie pan. blend the remaining ingredients for the filling, using 1/2 cup dates. Put the filling on top of the crust in the pie pan, spreading evenly. cover with foil. Bake for 45 minutes. Remove foil, bake for 15 additional minutes.','Pumpkin Pie, Healthy',NULL,'Can use 4 medjool dates or 8 regular dates for the crust.'),(4,3,3,1,'Blend all ingredients in a high-powered blender.','Ketchup',NULL,'If using medjool dates, use 7. 12 oz tomato paste is two small (6 oz) cans.'),(5,1,2,1,'Preheat oven to 350°F. Blend oats and almonds, place in bowl. blend beans, place in same bowl. Add remaining ingredients. Mix well. spread on baking sheet or form into burgers. Bake for 25 minutes.','Eric\'s Bean Burgers',NULL,'Can use one can of garbanzo beans. Can substitute shredded carrots with shredded zucchini.');
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `recipe_id` int NOT NULL,
  `score` int NOT NULL,
  `review_text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `user_id` (`user_id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`),
  CONSTRAINT `reviews_chk_1` CHECK ((`score` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,1,5,'Best recipe in the world there ever was!');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'contributor'),(3,'viewer');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `tag_id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Vegan'),(2,'Breakfast'),(3,'Lunch'),(4,'Dinner'),(5,'Vegetarian'),(6,'Gluten-Free'),(7,'Dairy-Free'),(8,'Low-Carb'),(9,'High-Protein'),(10,'Keto'),(11,'Paleo'),(12,'Whole30'),(13,'Quick'),(14,'Easy'),(15,'5 Ingredients or Less'),(16,'One-Pot'),(17,'Sheet Pan'),(18,'No-Bake'),(19,'Slow Cooker'),(20,'Instant Pot'),(21,'Air Fryer'),(22,'Grilled'),(23,'Baked'),(24,'Fried'),(25,'Raw'),(26,'Snack'),(27,'Dessert'),(28,'Appetizer'),(29,'Side Dish'),(30,'Main Course'),(31,'Soup'),(32,'Salad'),(33,'Pasta'),(34,'Pizza'),(35,'Sandwich'),(36,'Wrap'),(37,'Burger'),(38,'Rice'),(39,'Noodles'),(40,'Seafood'),(41,'Chicken'),(42,'Beef'),(43,'Pork'),(44,'Tofu'),(45,'Beans'),(46,'Chickpeas'),(47,'Lentils'),(48,'Eggs'),(49,'Chocolate'),(50,'Spicy'),(51,'Sweet'),(52,'Savory'),(53,'Comfort Food'),(54,'Healthy'),(55,'Kid-Friendly'),(56,'Holiday'),(57,'Christmas'),(58,'Thanksgiving'),(59,'Easter'),(60,'Halloween'),(61,'Asian'),(62,'Mexican'),(63,'Italian'),(64,'Indian'),(65,'Mediterranean'),(66,'Middle Eastern'),(67,'American'),(68,'French'),(69,'Thai'),(70,'Japanese'),(71,'Korean');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email` (`user_email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (user_id, role_id, user_name, user_email, password_hash) VALUES
  (1, 1, 'admin', 'admin@example.com', '$2a$10$WqRwwG0AhXCx3lLY6J8vsemrYSYumWo25dMwIVi9tuugmhnuaO3N.'),
  (2, 2, 'contributor', 'contributor@example.com', '$2a$10$aVFflvVqJKLI5kEsnPetH.0PbryD.rtQaKXXl8jCBPgCnqRB9sfku'),
  (3, 3, 'viewer', 'viewer@example.com', '$2a$10$XpNd4V5hOgoi/b9bZmW9Y.PVZ/uSPaWAXdKs2TJJh6FHfC/h/TOg.');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_favorites`
--

DROP TABLE IF EXISTS `users_favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_favorites` (
  `user_id` int NOT NULL,
  `recipe_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`recipe_id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `users_favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `users_favorites_ibfk_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_favorites`
--

LOCK TABLES `users_favorites` WRITE;
/*!40000 ALTER TABLE `users_favorites` DISABLE KEYS */;
INSERT INTO `users_favorites` VALUES (1,1);
/*!40000 ALTER TABLE `users_favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_notes`
--

DROP TABLE IF EXISTS `users_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_notes` (
  `user_id` int NOT NULL,
  `recipe_id` int NOT NULL,
  `note_text` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`recipe_id`),
  KEY `recipe_id` (`recipe_id`),
  CONSTRAINT `users_notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `users_notes_ibfk_2` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_notes`
--

LOCK TABLES `users_notes` WRITE;
/*!40000 ALTER TABLE `users_notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_notes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-27 10:31:30
