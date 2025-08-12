import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useMemo, useState } from 'react';
import { useFetch } from '../../api/useFetch';
import { apiFetch } from '../../api/apiFetch';
import type { RecipeDTO } from '../../types/recipe';
import RecipeForm, {
  type IngredientRowForm,
  type RecipeFormValues,
} from '../../components/forms/recipe/RecipeForm.tsx';

function RecipeEditView() {
  const { recipeId } = useParams<{ recipeId: string }>();
  const id = useMemo(() => Number(recipeId), [recipeId]);
  const navigate = useNavigate();

  const { data: recipe, loading, error } = useFetch<RecipeDTO>(`/api/recipes/${id}`);

  const [initialValues, setInitialValues] = useState<RecipeFormValues | null>(null);

  useEffect(() => {
    if (!recipe) return;

    // Map existing DTO → form values
    const rows: IngredientRowForm[] = recipe.recipeIngredients?.map((ri) => ({
      key: crypto.randomUUID(),
      ingredientId: ri.ingredientId, // ensure your RecipeIngredientDTO includes this
      quantity: ri.quantity != null ? String(ri.quantity) : '',
      unit: ri.unit ?? '',
      isOptional: ri.isOptional,
    })) ?? [{ key: crypto.randomUUID() }];

    const tagIds = (recipe.recipeTags ?? []).map((t) => t.tagId);

    setInitialValues({
      recipeName: recipe.recipeName,
      recipeInstructions: recipe.recipeInstructions,
      categoryId: recipe.categoryId ?? '',
      difficultyId: recipe.difficultyLevelId ?? '',
      rows,
      tagIds,
    });
  }, [recipe]);

  if (loading) return <p>Loading…</p>;
  if (error || !recipe) return <p>Recipe not found.</p>;
  if (!initialValues) return null;

  const handleSubmit = async (v: RecipeFormValues) => {
    const ingredientsPayload = v.rows
      .filter((r) => r.ingredientId)
      .map((r) => ({
        ingredientId: r.ingredientId!,
        quantity: r.quantity && r.quantity.trim() !== '' ? Number(r.quantity) : null,
        unit: r.unit && r.unit.trim() !== '' ? r.unit.trim() : null,
        isOptional: !!r.isOptional,
      }));

    await apiFetch<void>(`/api/recipes/${id}`, 'PUT', {
      recipeName: v.recipeName,
      recipeInstructions: v.recipeInstructions,
      categoryId: Number(v.categoryId),
      difficultyId: Number(v.difficultyId),
      ingredients: ingredientsPayload,
      tagIds: v.tagIds,
    });

    navigate(`/recipes/${id}`);
  };

  return (
    <>
      <h1>Edit Recipe</h1>
      <RecipeForm initialValues={initialValues} submitLabel="Update" onSubmit={handleSubmit} />
    </>
  );
}

export default RecipeEditView;
