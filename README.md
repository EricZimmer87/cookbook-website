# Cookbook Website (Cooking Made EZ)

[Live Demo](https://cookbook-website.onrender.com/)

This is a full-stack web application for discovering, creating, and sharing recipes. It is built with **React**, **Spring Boot**, and **MySQL**. Docker was used for development. This is my first application built with React and Spring Boot. It was used as a learning experience for the two frameworks.

## Features

- **Authentication & Authorization**
  - Secure JWT-based login/registration
  - Role-based access (Admin, Contributor, Viewer)
  - Forgot your password sends email with password reset link

- **Recipes**
  - Create, read, update, delete recipes
  - Search, sort, and filter recipes
  - Add a review for a recipe
  - Make notes about recipes for personal changes you made
  - Save recipes to favorites

## File Structure

```text
cookbook-website/
├── cookbook-backend/            # Spring Boot backend source code
├── cookbook-frontend/           # React frontend source code
├── initdb/
│   └── dump.sql                 # Creates DB with sample data (for Docker init)
├── Cookbook Website.png         # Original Figma whiteboard sketch (early DB design)
├── CookbookWebsiteCreateDB.sql  # Original DB creation script (PascalCase)
├── SnakeCaseDBCreate.sql        # Updated DB creation script (snake_case for Spring Boot)
├── ERD.png                      # Entity-Relationship Diagram of the DB
├── SampleData.txt               # Initial recipe data for testing
└── docker-compose.yml           # Docker config (mainly for database; backend commented out)
```

## Screenshots

## Deployment

The app is deployed by bundling the React frontend into the Spring Boot backend and hosting on **Render**.
The MySQL database is hosted on **Clever Cloud**.

- Backend & Frontend: [Render](https://render.com/)
- Database: [Clever Cloud](https://www.clever-cloud.com/)

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [React](https://react.dev/)
- [MySQL](https://www.mysql.com/)
- [Docker](https://www.docker.com/)
- Inspired by classic recipe-sharing platforms
