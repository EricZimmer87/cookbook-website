export interface RecipeIngredientDTO {
  recipeId: number;
  ingredientId: number;
  quantity: number;
  unit: string;
  ingredientName: string;
}

export interface RecipeDTO {
  recipeId: number;
  recipeName: string;
  recipeInstructions: string;
  recipeImage: string | null;
  categoryName: string;
  difficultyLevel: string;
  userName: string;
  recipeIngredients: RecipeIngredientDTO[];
}
