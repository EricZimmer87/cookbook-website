import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import type { ReviewCreateDTO } from '../../types/review.ts';
import ReviewForm from '../../components/forms/review/ReviewForm.tsx';
import { useAuth } from '../../context/useAuth.ts';

function ReviewCreateView() {
  const navigate = useNavigate();
  const { recipeId } = useParams();
  const { user } = useAuth();
  const location = useLocation();

  const handleSave = async (data: Partial<ReviewCreateDTO>) => {
    if (!user || !recipeId) {
      toast.error('Missing user or recipe ID');
      return;
    }

    const payload: ReviewCreateDTO = {
      recipeId: parseInt(recipeId),
      score: data.score ?? 0,
      reviewText: data.reviewText ?? '',
    };

    try {
      await apiFetch('/api/reviews', 'POST', payload);
      toast.success('Review created successfully.');
      if (location.state?.from) {
        navigate(location.state.from);
      } else {
        navigate('/');
      }
    } catch {
      toast.error('Error creating review');
    }
  };

  return (
    <div>
      <h1>Create Review</h1>
      <ReviewForm onSubmit={handleSave} />
    </div>
  );
}

export default ReviewCreateView;
