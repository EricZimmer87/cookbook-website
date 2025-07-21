import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import type {RecipeDTO} from "../../types/recipe.ts";

function RecipesView() {
    const [recipes, setRecipes] = useState<RecipeDTO[]>([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/recipes")
            .then((response) => response.json())
            .then((data: RecipeDTO[]) => setRecipes(data))
            .catch((error) => console.error("Error fetching recipes:", error));
    }, []);

    return (
        <div>
            <h1>All Recipes</h1>
            <ul>
                {recipes.map((recipe) => (
                    <li key={recipe.recipeId}>
                        <Link to={`/recipes/${recipe.recipeId}`}>
                            <h3>{recipe.recipeName}</h3>
                        </Link>
                        <ul>
                            {recipe.recipeIngredients.map((ingredient) => (
                                <li key={ingredient.ingredientId}>
                                    {ingredient.quantity} {ingredient.unit} {ingredient.ingredientName}
                                </li>
                            ))}
                        </ul>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default RecipesView;
