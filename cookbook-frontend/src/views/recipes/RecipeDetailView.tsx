import { useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe.ts';
import type { ReviewDTO } from '../../types/review.ts';
import Reviews from '../../components/review/Reviews.tsx';
import type { UserNotesDTO } from '../../types/user-notes.ts';
import UserNotes from '../../components/user-note/UserNotes.tsx';
import { useAuth } from '../../context/useAuth.ts';

function RecipeDetailView() {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();

  // Get recipes
  const {
    data: recipe,
    loading: recipeLoading,
    error: recipeError,
  } = useFetch<RecipeDTO>(`/api/recipes/${id}`);
  // Get reviews
  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/recipe/${id}`);
  // Get user notes
  const {
    data: userNote,
    loading: noteLoading,
    error: noteError,
  } = useFetch<UserNotesDTO>(
    user ? `/api/user-notes/recipe/${id}` : null
  );

  if (recipeLoading) return <p>Loading...</p>;
  if (recipeError)
    return <p style={{ color: 'red' }}>Error loading recipe or reviews.</p>;
  if (!recipe) return <p>Recipe not found.</p>;

  return (
    <div>
      <h1>Recipe Details</h1>
      <RecipeCard recipe={recipe} clickable={false} />

      {!noteLoading && !noteError && user && userNote?.noteText && (
        <UserNotes userNote={userNote} />
      )}


      {!reviewsLoading && !reviewsError && (
        <Reviews reviews={reviews || []} recipeId={recipe.recipeId} />
      )}
    </div>
  );
}

export default RecipeDetailView;
