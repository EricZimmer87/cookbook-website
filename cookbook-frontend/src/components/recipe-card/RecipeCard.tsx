import type { RecipeDTO } from '../../types/recipe';
import type { ReviewDTO } from '../../types/review';
import { useLocation, useNavigate } from 'react-router-dom';
import './RecipeCard.css';
import React from 'react';

type RecipeCardProps = {
  recipe: RecipeDTO;
  clickable?: boolean;
  showReviews?: boolean;
  reviews?: ReviewDTO[];
  canDelete?: boolean;
  onDelete?: (recipeId: number) => void;
};

function RecipeCard({ recipe, clickable = false, canDelete = false, onDelete }: RecipeCardProps) {
  const navigate = useNavigate();
  const location = useLocation();

  const handleCardClick = () => {
    if (clickable) {
      navigate(`/recipes/${recipe.recipeId}`, { state: { from: location.pathname } });
    }
  };

  const handleDeleteClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    e.preventDefault();
    onDelete?.(recipe.recipeId);
  };

  const handleEditClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    e.preventDefault();
    navigate(`/recipes/${recipe.recipeId}/edit`, { state: { from: location.pathname } });
  };

  return (
    <div
      className={`recipe-card ${clickable ? 'clickable' : ''}`}
      onClick={handleCardClick}
      role={clickable ? 'button' : undefined}
      tabIndex={clickable ? 0 : undefined}
      onKeyDown={(e) => {
        if (clickable && e.key === 'Enter') {
          e.stopPropagation();
          handleCardClick();
        }
      }}
    >
      <div className="recipe-card__header">
        <h3 className="recipe-card__title">{recipe.recipeName}</h3>
        <p>
          <small>Uploaded by {recipe.userName}</small>
        </p>
      </div>

      <ul>
        {recipe.recipeIngredients?.map((ingredient) => (
          <li key={ingredient.ingredientId}>
            {ingredient.ingredientName}, {ingredient.quantity} {ingredient.unit}
          </li>
        ))}
      </ul>

      {recipe.ingredientsNotes && (
        <p>
          <strong>Note: </strong>
          {recipe.ingredientsNotes}
        </p>
      )}

      <p>{recipe.recipeInstructions}</p>

      {canDelete && onDelete && (
        <div>
          <button
            type="button"
            className="button button-red"
            onClick={handleDeleteClick}
            onKeyDown={(e) => e.stopPropagation()}
          >
            Delete
          </button>
          <button
            type="button"
            className="button button-blue"
            onClick={handleEditClick}
            onKeyDown={(e) => e.stopPropagation()}
          >
            Edit Recipe
          </button>
        </div>
      )}
    </div>
  );
}

export default RecipeCard;
