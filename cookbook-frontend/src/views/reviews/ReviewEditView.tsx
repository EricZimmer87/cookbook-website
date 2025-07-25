import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { ReviewDTO } from '../../types/review.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import ReviewForm from '../../components/forms/review/ReviewForm.tsx';

function ReviewEditView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: review, loading: reviewLoading } = useFetch<ReviewDTO>(`/api/reviews/${id}`);

  if (reviewLoading) return <p>Loading...</p>;
  if (!review) return <p>Review not found.</p>;

  const handleSave = async (data: Partial<ReviewDTO>) => {
    await apiFetch(`/api/reviews/${id}`, 'PUT', data);
    navigate(`/users/${review.userId}`);
  };

  return (
    <div>
      <h1>Edit Review</h1>
      <ReviewForm defaultValues={review} onSubmit={handleSave} />
    </div>
  );
}

export default ReviewEditView;
