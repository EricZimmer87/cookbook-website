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
