import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useFetch } from '../../api/useFetch.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe.ts';
import type { ReviewDTO } from '../../types/review.ts';
import Reviews from '../../components/review/Reviews.tsx';
import type { UserNotesDTO } from '../../types/user-notes.ts';
import UserNotes from '../../components/user-note/UserNotes.tsx';
import { useAuth } from '../../context/useAuth.ts';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';
import Button from '../../components/buttons/Button.tsx';
import { FaHeart } from 'react-icons/fa';
import AddButton from '../../components/buttons/AddButton.tsx';

function RecipeDetailView() {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();
  const [optimisticFavorited, setOptimisticFavorited] = useState<boolean | null>(null);

  // Get recipes
  const {
    data: recipe,
    loading: recipeLoading,
    error: recipeError,
  } = useFetch<RecipeDTO>(`/api/recipes/${id}`);
  useErrorRedirect(recipeError);

  // Get reviews
  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/recipe/${id}`);
  useErrorRedirect(reviewsError);

  // Get user notes
  const {
    data: userNote,
    loading: noteLoading,
    error: noteError,
  } = useFetch<UserNotesDTO>(user ? `/api/user-notes/recipe/${id}` : null);
  useErrorRedirect(noteError);

  // Check if recipe is favorited (only if user is logged in)
  const {
    data: isFavorited,
    loading: favoriteLoading,
    error: favoriteError,
  } = useFetch<boolean>(user ? `/api/user-favorites/user/${user.userId}/recipe/${id}` : null);
  useErrorRedirect(favoriteError);

  // Use optimistic state if available, otherwise use fetched data
  const actuallyFavorited = optimisticFavorited !== null ? optimisticFavorited : isFavorited;

  const handleAddToFavorites = async () => {
    try {
      setOptimisticFavorited(true); // Optimistic update
      await apiFetch('/api/user-favorites', 'POST', {
        userId: user?.userId,
        recipeId: parseInt(id!), // Use 'id' instead of 'recipeId'
      });
    } catch (error) {
      setOptimisticFavorited(false); // Revert on error
      console.error('Failed to add to favorites:', error);
    }
  };

  const handleRemoveFromFavorites = async () => {
    try {
      setOptimisticFavorited(false); // Optimistic update
      await apiFetch(`/api/user-favorites/user/${user?.userId}/recipe/${id}`, 'DELETE'); // Use 'id' instead of 'recipeId'
    } catch (error) {
      setOptimisticFavorited(true); // Revert on error
      console.error('Failed to remove from favorites:', error);
    }
  };

  if (recipeLoading) return <p>Loading...</p>;
  if (recipeError) return <p style={{ color: 'red' }}>Error loading recipe or reviews.</p>;
  if (!recipe) return <p>Recipe not found.</p>;

  return (
    <div>
      <h1>Recipe Details</h1>
      <RecipeCard recipe={recipe} clickable={false} />

      {/* Favorite button - only show if user is logged in */}
      {user &&
        !favoriteLoading &&
        (actuallyFavorited ? (
          <Button type="button" onClick={handleRemoveFromFavorites} className="button-red">
            <FaHeart className="button-icon" />
            Remove from Favorites
          </Button>
        ) : (
          <AddButton onClick={handleAddToFavorites}>Add to Favorites</AddButton>
        ))}

      {/* User Notes */}
      {!noteLoading && !noteError && user && userNote?.noteText && (
        <UserNotes userNote={userNote} />
      )}

      {/* Reviews */}
      {!reviewsLoading && !reviewsError && (
        <Reviews reviews={reviews || []} recipeId={recipe.recipeId} />
      )}
    </div>
  );
}

export default RecipeDetailView;
