import type { RecipeDTO } from '../../types/recipe.ts';
import type { ReviewDTO } from '../../types/review.ts';
import { useNavigate } from 'react-router-dom';
import './RecipeCard.css';
import AddButton from '../buttons/AddButton.tsx';
import { useAuth } from '../../context/useAuth.ts';

type RecipeCardProps = {
  recipe: RecipeDTO;
  clickable?: boolean;
  showReviews?: boolean;
  reviews?: ReviewDTO[];
};

function RecipeCard({
  recipe,
  clickable = false,
  showReviews = false,
  reviews = [],
}: RecipeCardProps) {
  const navigate = useNavigate();

  const handleClick = () => {
    if (clickable) {
      navigate(`/recipes/${recipe.recipeId}`);
    }
  };

  const { user } = useAuth();
  const hasUserReviewed = user && reviews.some((r) => r.userId === user.userId);

  return (
    <div
      className={`recipe-card ${clickable ? 'clickable' : ''}`}
      onClick={handleClick}
      role={clickable ? 'button' : undefined}
      tabIndex={clickable ? 0 : undefined}
      onKeyDown={(e) => clickable && e.key === 'Enter' && handleClick()}
    >
      <h3>{recipe.recipeName}</h3>
      <ul>
        {recipe.recipeIngredients.map((ingredient) => (
          <li key={ingredient.ingredientId}>
            {ingredient.quantity} {ingredient.unit} {ingredient.ingredientName}
          </li>
        ))}
      </ul>
      <p>{recipe.recipeInstructions}</p>

      {showReviews && (
        <div className="recipe-reviews">
          <h4>Reviews:</h4>

          {user && !hasUserReviewed && (
            <AddButton onClick={() => navigate(`/reviews/${recipe.recipeId}/new`)} />
          )}

          {reviews.length > 0 ? (
            <ul>
              {reviews.map((review) => (
                <li key={review.reviewId}>
                  <strong>{review.userName}</strong>: {review.reviewText} ({review.score}/5)
                </li>
              ))}
            </ul>
          ) : (
            <p>No reviews.</p>
          )}
        </div>
      )}
    </div>
  );
}

export default RecipeCard;
