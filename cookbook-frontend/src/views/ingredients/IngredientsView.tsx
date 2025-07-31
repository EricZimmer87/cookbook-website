import AddButton from '../../components/buttons/AddButton.tsx';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useFetch } from '../../api/useFetch.ts';
import type { IngredientDTO } from '../../types/ingredient.ts';
import { useNavigate, useLocation } from 'react-router-dom';

function IngredientsView() {
  const {
    data: ingredients,
    loading: ingredientsLoading,
    error
  } = useFetch<IngredientDTO[]>('/api/ingredients');

  const navigate = useNavigate();
  const location = useLocation();

  if (ingredientsLoading) return <p>Loading...</p>;
  if (error) return <p>Error loading tags...</p>;
  if (!ingredients || ingredients.length === 0) return <p>No ingredients found.</p>;

  return (
    <div>
      <h1>All Ingredients</h1>
      <AddButton onClick={() => navigate('/ingredients/new', { state: { from: location.pathname } })} />
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        {[...ingredients]
          .sort((a, b) => a.ingredientName.localeCompare(b.ingredientName))
          .map((ingredient) => (
            <tr key={ingredient.ingredientId}>
              <td>{ingredient.ingredientId}</td>
              <td>{ingredient.ingredientName}</td>
              <td>
                <EditButton onClick={() => navigate(`/ingredients/${ingredient.ingredientId}/edit`, { state: { from: location.pathname } })} />
                <DeleteButton onClick={() => navigate(`/ingredients/${ingredient.ingredientId}/delete`, { state: { from: location.pathname } })} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default IngredientsView;