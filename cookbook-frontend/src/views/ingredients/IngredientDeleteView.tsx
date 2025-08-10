import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';
import { ApiError, apiFetch } from '../../api/apiFetch.ts';
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
      if (err instanceof ApiError) {
        if (err.status === 409) {
          // friendly FK message from backend
          toast.error(err.body || 'Cannot delete ingredient: it is used by one or more recipes.');
          return;
        }
        if (err.status === 403) {
          navigate('/forbidden');
          return;
        }
        if (err.status === 404) {
          navigate('/not-found');
          return;
        }
        toast.error(err.body || `Error ${err.status}`);
        return;
      }
      // Fallback unknown error
      console.error(err);
      toast.error('Failed to delete ingredient.');
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
