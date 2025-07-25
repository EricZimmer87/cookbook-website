export interface ReviewDTO {
  reviewId: number;
  userId: number;
  userName: string;
  recipeId: number;
  recipeName: string;
  score: number;
  reviewText: string;
}
