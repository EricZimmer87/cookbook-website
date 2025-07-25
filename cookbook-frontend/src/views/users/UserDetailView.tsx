import { Link, useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch';
import type { UserDTO } from '../../types/user';
import type { ReviewDTO } from '../../types/review';
import EditButton from '../../components/buttons/EditButton.tsx';
import type { RecipeDTO } from '../../types/recipe.ts';
import type { FavoriteDTO } from '../../types/favorite.ts';

function UserDetailView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: user,
    loading: userLoading,
    error: userError,
  } = useFetch<UserDTO>(`/api/users/${id}`);

  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/user/${id}`);

  const {
    data: recipes,
    loading: recipesLoading,
    error: recipesError,
  } = useFetch<RecipeDTO[]>(`/api/recipes?userId=${id}`);

  const {
    data: favorites,
    loading: favoritesLoading,
    error: favoritesError,
  } = useFetch<FavoriteDTO[]>(`/api/user-favorites/user/${id}`);

  // Combine loading states
  if (userLoading || reviewsLoading || recipesLoading || favoritesLoading)
    return <p>Loading user...</p>;

  // Combine error states
  if (userError || reviewsError || recipesError || favoritesError)
    return <p style={{ color: 'red' }}>Error: {userError || reviewsError}</p>;

  if (!user) return <p>User not found.</p>;

  return (
    <div>
      <h1>{user.userName}</h1>
      <p>Username: {user.userName}</p>
      <p>Email: {user.userEmail}</p>
      <p>Role: {user.role.roleName}</p>
      <Link to={`/users/${user.userId}/edit`}>
        <EditButton />
      </Link>

      <h2>{user.userName}'s Favorite Recipes</h2>
      {favorites && favorites.length > 0 ? (
        <ul>
          {favorites.map((favorite) => (
            <li key={favorite.userId}>
              <Link to={`/recipes/${favorite.recipeId}`}>{favorite.recipeName}</Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>No favorites.</p>
      )}

      <h2>Recipes by {user.userName}</h2>
      {recipes && recipes.length > 0 ? (
        <ul>
          {recipes.map((recipe) => (
            <li key={recipe.recipeId}>
              <Link to={`/recipes/${recipe.recipeId}`}>{recipe.recipeName}</Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>No recipes.</p>
      )}

      <h2>Reviews by {user.userName}</h2>
      {reviews && reviews.length > 0 ? (
        <ul>
          {reviews.map((review) => (
            <li key={review.reviewId}>
              <strong>{review.recipeName}</strong>: {review.reviewText} ({review.score}/5)
              <EditButton onClick={() => navigate(`/reviews/${review.reviewId}/edit`)} />
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
