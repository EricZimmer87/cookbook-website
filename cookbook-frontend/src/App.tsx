import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Layout from './components/layout/Layout.tsx';
import RecipesView from './views/recipes/RecipesView';
import RecipeDetailView from './views/recipes/RecipeDetailView';
import LoginView from './views/auth/LoginView';
import RegisterView from './views/auth/RegisterView';
import UsersView from './views/users/UsersView';
import UserDetailView from './views/users/UserDetailView.tsx';
import UserEditView from './views/users/UserEditView.tsx';
import UserDeleteView from './views/users/UserDeleteView.tsx';
import { Toaster } from 'react-hot-toast';
import UserProfileView from './views/users/UserProfileView.tsx';
import ReviewEditView from './views/reviews/ReviewEditView';
import TagsView from './views/tags/TagsView.tsx';
import TagEditView from './views/tags/TagEditView.tsx';
import TagCreateView from './views/tags/TagCreateView.tsx';
import TagDeleteView from './views/tags/TagDeleteView.tsx';
import CategoriesView from './views/categories/CategoriesView';
import CategoryEditView from './views/categories/CategoryEditView.tsx';
import CategoryDeleteView from './views/categories/CategoryDeleteView.tsx';
import CategoryCreateView from './views/categories/CategoryCreateView.tsx';
import DifficultyLevelsView from './views/difficulty-level/DifficultyLevelsView.tsx';
import DifficultyLevelCreateView from './views/difficulty-level/DifficultyLevelCreateView.tsx';
import DifficultyLevelEditView from './views/difficulty-level/DifficultyLevelEditView.tsx';
import DifficultyLevelDeleteView from './views/difficulty-level/DifficultyLevelDeleteView.tsx';
import IngredientsView from './views/ingredients/IngredientsView.tsx';
import IngredientCreateView from './views/ingredients/IngredientCreateView.tsx';
import IngredientEditView from './views/ingredients/IngredientEditView.tsx';
import IngredientDeleteView from './views/ingredients/IngredientDeleteView.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />, // Navbar is here
    children: [
      { index: true, element: <h1>Home Page</h1> },
      { path: 'recipes', element: <RecipesView /> },
      { path: 'recipes/:id', element: <RecipeDetailView /> },
      { path: 'login', element: <LoginView /> },
      { path: 'register', element: <RegisterView /> },
      { path: 'users', element: <UsersView /> },
      { path: '/users/:id', element: <UserDetailView /> },
      { path: '/users/:id/edit', element: <UserEditView /> },
      { path: '/users/:id/delete', element: <UserDeleteView /> },
      { path: '/users/:id/profile', element: <UserProfileView /> },
      { path: '/reviews/:id/edit', element: <ReviewEditView /> },
      { path: '/tags', element: <TagsView /> },
      { path: '/tags/new', element: <TagCreateView /> },
      { path: '/tags/:id/edit', element: <TagEditView /> },
      { path: '/tags/:id/delete', element: <TagDeleteView /> },
      { path: '/categories', element: <CategoriesView /> },
      { path: '/categories/new', element: <CategoryCreateView /> },
      { path: '/categories/:id/edit', element: <CategoryEditView /> },
      { path: '/categories/:id/delete', element: <CategoryDeleteView /> },
      { path: '/difficulty-levels', element: <DifficultyLevelsView /> },
      { path: '/difficulty-levels/new', element: <DifficultyLevelCreateView /> },
      { path: '/difficulty-levels/:id/edit', element: <DifficultyLevelEditView /> },
      { path: '/difficulty-levels/:id/delete', element: <DifficultyLevelDeleteView /> },
      { path: '/ingredients', element: <IngredientsView /> },
      { path: '/ingredients/new', element: <IngredientCreateView /> },
      { path: '/ingredients/:id/edit', element: <IngredientEditView /> },
      { path: '/ingredients/:id/delete', element: <IngredientDeleteView /> },
    ],
  },
]);

function App() {
  return (
    <>
      <Toaster position="top-right" />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
