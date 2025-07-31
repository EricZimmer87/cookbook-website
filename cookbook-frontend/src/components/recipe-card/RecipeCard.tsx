import type { RecipeDTO } from '../../types/recipe.ts';
import type { ReviewDTO } from '../../types/review.ts';
import { useNavigate, useLocation } from 'react-router-dom';
import './RecipeCard.css';

type RecipeCardProps = {
  recipe: RecipeDTO;
  clickable?: boolean;
  showReviews?: boolean;
  reviews?: ReviewDTO[];
};

function RecipeCard({
  recipe,
  clickable = false,
}: RecipeCardProps) {
  const navigate = useNavigate();
  const location = useLocation();

  const handleClick = () => {
    if (clickable) {
      navigate(`/recipes/${recipe.recipeId}`, { state: { from: location.pathname } });
    }
  };

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

    </div>
  );
}

export default RecipeCard;
