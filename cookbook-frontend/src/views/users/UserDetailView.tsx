import { Link, useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch';
import type { UserDTO } from '../../types/user';
import type { ReviewDTO } from '../../types/review';
import EditButton from '../../components/buttons/EditButton.tsx';
import type { RecipeDTO } from '../../types/recipe.ts';
import type { FavoriteDTO } from '../../types/favorite.ts';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';
import Button from '../../components/buttons/Button.tsx';
import { useAuth } from '../../context/useAuth.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import { useEffect, useState } from 'react';

function UserDetailView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user: authUser } = useAuth();
  const [recipeList, setRecipeList] = useState<RecipeDTO[]>([]);

  const {
    data: user,
    loading: userLoading,
    error: userError,
  } = useFetch<UserDTO>(`/api/users/${id}`);
  useErrorRedirect(userError);

  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/user/${id}`);
  useErrorRedirect(reviewsError);

  const {
    data: recipes,
    loading: recipesLoading,
    error: recipesError,
  } = useFetch<RecipeDTO[]>(`/api/recipes?userId=${id}`);
  useErrorRedirect(recipesError);
  useEffect(() => {
    if (recipes) setRecipeList(recipes);
  }, [recipes]);

  const {
    data: favorites,
    loading: favoritesLoading,
    error: favoritesError,
  } = useFetch<FavoriteDTO[]>(`/api/user-favorites/user/${id}`);
  useErrorRedirect(favoritesError);

  // Combine loading states
  if (userLoading || reviewsLoading || recipesLoading || favoritesLoading)
    return <p>Loading user...</p>;
  if (!user) return <p>User not found.</p>;

  const pageUserId = Number(id);
  const isAdmin = authUser?.role?.roleName?.toLowerCase() === 'admin';

  const handleDelete = async (recipeId: number) => {
    if (!window.confirm('Delete this recipe? This cannot be undone.')) return;
    const prev = recipeList;
    setRecipeList(prev.filter((r) => r.recipeId !== recipeId));
    try {
      await apiFetch<void>(`/api/recipes/${recipeId}`, 'DELETE');
    } catch (e: any) {
      setRecipeList(prev);
      alert(e.body || 'Failed to delete recipe');
    }
  };

  return (
    <div>
      <h1>{user.userName}</h1>
      <p>Username: {user.userName}</p>
      <p>Email: {user.userEmail}</p>
      <p>Role: {user.role.roleName}</p>
      <Link to={`/users/${user.userId}/edit`}>
        <EditButton />
      </Link>
      {/* Only show for same user */}
      {authUser && authUser.userId === pageUserId && (
        <Button className="button-blue" onClick={() => navigate('/auth/change-password')}>
          Change password
        </Button>
      )}

      {/* Edit Role - Admin Only */}
      {isAdmin && authUser?.userId !== pageUserId && (
        <Button className="button-blue" onClick={() => navigate(`/users/${user.userId}/edit/role`)}>
          Change Role
        </Button>
      )}

      <h2>{user.userName}'s Favorite Recipes</h2>
      {favorites && favorites.length > 0 ? (
        <ul>
          {favorites.map((favorite) => (
            <li key={`${favorite.userId}-${favorite.recipeId}`}>
              <Link to={`/recipes/${favorite.recipeId}`}>{favorite.recipeName}</Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>No favorites.</p>
      )}

      {!!authUser &&
        ['admin', 'contributor'].includes(authUser.role?.roleName?.toLowerCase() || '') && (
          <>
            <h2>Recipes by {user.userName}</h2>

            {recipeList?.length ? (
              <ul>
                {recipeList.map((recipe) => (
                  <li key={recipe.recipeId}>
                    <Link to={`/recipes/${recipe.recipeId}`}>{recipe.recipeName}</Link>
                    <DeleteButton onClick={() => handleDelete(recipe.recipeId)} />
                    <EditButton onClick={() => navigate(`/recipes/${recipe.recipeId}/edit`)} />
                  </li>
                ))}
              </ul>
            ) : (
              <p>No recipes.</p>
            )}
          </>
        )}

      <h2>Reviews by {user.userName}</h2>
      {reviews && reviews.length > 0 ? (
        <ul>
          {reviews.map((review) => (
            <li key={review.reviewId}>
              <strong>{review.recipeName}</strong>: {review.reviewText} ({review.score}/5)
              <EditButton onClick={() => navigate(`/reviews/${review.reviewId}/edit`)} />
              <DeleteButton onClick={() => navigate(`/reviews/${review.reviewId}/delete`)} />
            </li>
          ))}
        </ul>
      ) : (
        <p>No reviews.</p>
      )}
    </div>
  );
}

export default UserDetailView;
