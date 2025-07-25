# Project Notes

A quick reference for major components, views, and organization patterns in the app.

---

## Components

### Buttons (`/src/components/buttons`)

- `Button.tsx`: Base button used for consistent styles and behavior. Accepts `onClick`, `type`, and `className`.
- Specialized buttons:
  - `EditButton.tsx`
  - `DeleteButton.tsx`
  - `SaveButton.tsx`
  - etc.
- _To update appearance for all buttons, modify `index.css`. To update logic or icons, edit the specific button file._

---

### Forms (`/src/components/forms`)

- All forms use **React Hook Form**.
- Each form component is reused for both **Create (POST)** and **Edit (PUT)** operations.
- Common forms:
  - `UserForm.tsx`
  - `RecipeForm.tsx` _(planned)_
  - etc.
- Fields use the `register()` function from React Hook Form and handle validation inline.

---

### Recipes (`/src/components/recipes`)

- `RecipeCard.tsx`: Shared display component used in:
  - `RecipesView.tsx` (list of all recipes)
  - `RecipeDetailView.tsx` (single recipe detail)

---

## Views

### Users

- `UsersView.tsx`: Displays all users in a table. Includes buttons for Edit/Delete.
- `UserEditView.tsx`: Loads selected user and shows `UserForm.tsx` pre-filled.
- `UserDeleteView.tsx`: Confirmation page for deletion.
- `RegisterView.tsx`: Form to create/register a new user.

### Recipes

- `RecipesView.tsx`: Displays all recipes using `RecipeCard.tsx`.
- `RecipeDetailView.tsx`: Shows a single recipe with detailed info.
- _(others to come)_

---

## Utilities

### `apiFetch.ts`

- Wrapper for `fetch()` that handles headers, token, JSON parsing, and error handling.
- Supports `'POST' | 'PUT' | 'DELETE'` requests.
- Automatically includes token from `localStorage`.

### `useFetch.ts`

- Custom hook for GET requests.
- Handles loading and error states.

---

## Styles

### `index.css`

- Main global stylesheet.
- Contains styles for:
  - `.form`
  - `.form-group`
  - `.button`, `.button-blue`, `.button-red`, etc.
  - table styling

---

## TODO

- [ ] Create `RecipeForm.tsx` for adding/editing recipes
- [ ] Add pagination or filtering for Users or Recipes
- [ ] Add auth guard for protected routes
