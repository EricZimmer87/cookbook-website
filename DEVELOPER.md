# Developer Guide

## Frontend Design Notes

I built the frontend with **code reuse in mind from the start**, applying lessons learned from past experience:

- **Reusable Buttons**
  - Instead of duplicating edit/save/cancel buttons across the app, I created a generic `Button` component and extended it into `EditButton`, `SaveButton`, etc.
  - This allows changes (e.g., updating an icon) to be made in a single place rather than scattered across multiple files.
  - Initially, due to my limited React experience, I fell back on regular `<button>` elements in some areas, but after learning more, I refactored them to be more flexible and consistent.

- **Reusable Forms**
  - The form for creating a recipe and the form for editing one share almost all logic.
  - To avoid duplication, I built reusable form components that handle both creation and editing.
  - This same pattern applies to other CRUD entities like tags and ingredients.

- **API Utilities**
  - Since multiple components needed to call the API, I created `apiFetch.ts` and `useFetch.ts` utilities for consistent and reusable data fetching across the app.

- **Styling**
  - Styling was not the main focus of this project (the focus was React + Spring Boot).
  - A global CSS file handles shared UI elements such as forms and tables, while view-specific CSS files live alongside their respective components for scoped styling.

## Backend Design Notes

The backend follows standard Spring Boot conventions with a clear separation of concerns:

- **Models (Entities)**
  - Each database table is represented by a model class.
  - These define the schema and relationships for persistence via JPA/Hibernate.

- **Repositories**
  - Each entity has a repository interface extending `JpaRepository`, providing built-in CRUD operations.
  - Custom queries are defined where needed for more specific lookups.

- **Services**
  - Business logic is implemented in service classes.
  - Each service is split into an interface and an implementation for clarity and testability.
  - Services sit between controllers and repositories, keeping controllers small.

- **Controllers**
  - Define REST API endpoints for each entity.
  - Responsible only for routing requests and delegating to services.

- **Security**
  - `WebConfig` manages CORS configuration.
  - `SecurityConfig` enforces authentication and role-based permissions for API calls.
  - Includes logic for banning users.

- **Exception Handling**
  - Custom exceptions handle edge cases such as preventing deletion of ingredients or recipes that are still in use.
  - A `GlobalExceptionHandler` translates exceptions into clear HTTP responses (e.g., conflict status).
