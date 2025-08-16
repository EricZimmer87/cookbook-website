export interface RecipeIngredientDTO {
  recipeId: number;
  ingredientId: number;
  quantity: number;
  unit: string;
  ingredientName: string;
  isOptional: boolean;
}

export interface RecipeTagDTO {
  recipeId: number;
  ownerId: number;
  recipeName: string;
  tagId: number;
  tagName: string;
}

export interface RecipeDTO {
  recipeId: number;
  ownerId: number;
  recipeName: string;
  recipeInstructions: string;
  ingredientsNotes: string;
  recipeImage?: string | null;
  categoryId: number;
  categoryName?: string;
  difficultyLevelId?: number;
  difficultyLevel?: string;
  userName?: string;
  recipeIngredients?: RecipeIngredientDTO[];
  recipeTags?: RecipeTagDTO[];
}
