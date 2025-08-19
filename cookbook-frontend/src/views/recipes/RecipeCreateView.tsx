import { useNavigate } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch';
import type { RecipeDTO } from '../../types/recipe';

import RecipeForm, { type RecipeFormValues } from '../../components/forms/recipe/RecipeForm.tsx';

function RecipeCreateView() {
  const navigate = useNavigate();

  const handleSubmit = async (v: RecipeFormValues) => {
    const ingredientsPayload = v.rows
      .filter((r) => r.ingredientId)
      .map((r) => ({
        ingredientId: r.ingredientId!,
        quantity: r.quantity && r.quantity.trim() !== '' ? r.quantity.trim() : null,
        unit: r.unit && r.unit.trim() !== '' ? r.unit.trim() : null,
        isOptional: !!r.isOptional,
      }));

    const dto = await apiFetch<RecipeDTO>('/api/recipes', 'POST', {
      recipeName: v.recipeName,
      recipeInstructions: v.recipeInstructions,
      ingredientsNotes: v.ingredientsNotes,
      categoryId: Number(v.categoryId),
      difficultyId: Number(v.difficultyId),
      ingredients: ingredientsPayload,
      tagIds: v.tagIds,
    });

    navigate(`/recipes/${dto?.recipeId ?? ''}`);
  };

  return (
    <>
      <h1>Add a Recipe</h1>
      <RecipeForm submitLabel="Create" onSubmit={handleSubmit} />
    </>
  );
}

export default RecipeCreateView;
