import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { IngredientDTO } from '../../types/ingredient.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import IngredientForm from '../../components/forms/tag/IngredientForm.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function IngredientEditView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: ingredient,
    loading: ingredientLoading,
    error: ingredientError,
  } = useFetch<IngredientDTO>(`/api/ingredients/${id}`);
  useErrorRedirect(ingredientError);

  if (ingredientLoading) return <p>Loading...</p>;
  if (!ingredient) return <p>Ingredient not found.</p>;

  const handleSave = async (data: Partial<IngredientDTO>) => {
    await apiFetch(`/api/ingredients/${id}`, 'PUT', data);
    navigate('/ingredients');
  };

  return (
    <div>
      <h1>Edit Ingredients</h1>
      <IngredientForm defaultValues={ingredient} onSubmit={handleSave} />
    </div>
  );
}

export default IngredientEditView;
