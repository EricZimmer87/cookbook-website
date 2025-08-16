import type { ReviewDTO } from '../../types/review.ts';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import EditButton from '../buttons/EditButton.tsx';
import DeleteButton from '../buttons/DeleteButton.tsx';
import AddButton from '../buttons/AddButton.tsx';
import './Reviews.css';

type ReviewsProps = {
  reviews: ReviewDTO[];
  recipeId: number;
};

function Reviews({ reviews, recipeId }: ReviewsProps) {
  const { user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const sortedReviews = [...reviews];
  if (user) {
    sortedReviews.sort((a, b) => {
      if (a.userId === user.userId) return -1;
      if (b.userId === user.userId) return 1;
      return 0;
    });
  }

  const hasUserReviewed = user && reviews.some((r) => r.userId === user.userId);

  return (
    <div className="recipe-reviews">
      <h4>Reviews:</h4>

      {user && !hasUserReviewed && (
        <AddButton
          onClick={() =>
            navigate(`/reviews/${recipeId}/new`, { state: { from: location.pathname } })
          }
        >
          Add Review
        </AddButton>
      )}

      {sortedReviews.length > 0 ? (
        <ul className="reviews-list">
          {sortedReviews.map((review) => (
            <li key={review.reviewId} className="review-card">
              <article className="card">
                <h3 className="review-user">{review.userName}</h3>
                <p className="review-score">Score: {review.score}/5</p>
                <p className="review-text">{review.reviewText}</p>

                {user && review.userId === user.userId && (
                  <div className="review-actions">
                    <EditButton
                      onClick={() =>
                        navigate(`/reviews/${review.reviewId}/edit`, {
                          state: { from: location.pathname },
                        })
                      }
                    />
                    <DeleteButton
                      onClick={() =>
                        navigate(`/reviews/${review.reviewId}/delete`, {
                          state: { from: location.pathname },
                        })
                      }
                    />
                  </div>
                )}
              </article>
            </li>
          ))}
        </ul>
      ) : (
        <p>No reviews.</p>
      )}
    </div>
  );
}

export default Reviews;
