export interface RecipeIngredientDTO {
  recipeId: number;
  ingredientId: number;
  quantity: number;
  unit: string;
  ingredientName: string;
}

export interface RecipeTagDTO {
  recipeId: number;
  recipeName: string;
  tagId: number;
  tagName: string;
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
  recipeTags: RecipeTagDTO[];
}
