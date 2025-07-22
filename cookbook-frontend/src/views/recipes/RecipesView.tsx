import { useFetch } from '../../api/useFetch.ts';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe.ts';

function RecipesView() {
  const { data: recipes, loading, error } = useFetch<RecipeDTO[]>('/api/recipes');

  if (loading) {
    return <p>Loading recipes...</p>;
  }

  if (error) {
    return <p style={{ color: 'red' }}>Error: {error}</p>;
  }

  return (
    <div>
      <h1>All Recipes</h1>
      {recipes?.map((recipe) => (
        <RecipeCard key={recipe.recipeId} recipe={recipe} clickable={true} showReviews={false} />
      ))}
    </div>
  );
}

export default RecipesView;
