import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Layout from './components/layout/Layout.tsx';
import RecipesView from './views/recipes/RecipesView';
import RecipeDetailView from './views/recipes/RecipeDetailView';
import LoginView from './views/auth/LoginView';
import RegisterView from './views/auth/RegisterView';
import UsersView from './views/users/UsersView';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />, // 🪄 Navbar is here
    children: [
      { index: true, element: <h1>Home Page</h1> },
      { path: 'recipes', element: <RecipesView /> },
      { path: 'recipes/:id', element: <RecipeDetailView /> },
      { path: 'login', element: <LoginView /> },
      { path: 'register', element: <RegisterView /> },
      { path: 'users', element: <UsersView /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
