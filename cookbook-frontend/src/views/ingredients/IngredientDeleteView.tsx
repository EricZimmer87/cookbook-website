import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import { useFetch } from '../../api/useFetch.ts';
import { useNavigate, useParams } from 'react-router-dom';
import type { IngredientDTO } from '../../types/ingredient.ts';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function IngredientDeleteView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: ingredient,
    loading: ingredientLoading,
    error: ingredientError,
  } = useFetch<IngredientDTO>(`/api/ingredients/${id}`);
  useErrorRedirect(ingredientError);

  if (ingredientLoading) return <p>Loading...</p>;
  if (ingredientError) return <p>Error</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/ingredients/${id}`, 'DELETE');
      toast.success('Ingredient deleted successfully.');
      navigate('/ingredients');
    } catch (err) {
      console.error(err);
      alert('Failed to delete ingredient.');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete <strong>{ingredient?.ingredientName}</strong>
      </p>
      <div style={{ marginTop: '1rem' }}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  );
}

export default IngredientDeleteView;
