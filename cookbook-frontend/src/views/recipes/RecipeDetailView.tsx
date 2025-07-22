import { useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe.ts';
import type { ReviewDTO } from '../../types/review.ts';

function RecipeDetailView() {
  const { id } = useParams<{ id: string }>();

  const {
    data: recipe,
    loading: recipeLoading,
    error: recipeError,
  } = useFetch<RecipeDTO>(`/api/recipes/${id}`);
  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/recipe/${id}`);

  if (recipeLoading || reviewsLoading) return <p>Loading...</p>;
  if (recipeError || reviewsError)
    return <p style={{ color: 'red' }}>Error loading recipe or reviews.</p>;
  if (!recipe) return <p>Recipe not found.</p>;

  return (
    <div>
      <h1>Recipe Details</h1>
      <RecipeCard recipe={recipe} clickable={false} showReviews={true} reviews={reviews || []} />
    </div>
  );
}

export default RecipeDetailView;
