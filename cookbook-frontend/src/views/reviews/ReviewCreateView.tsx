import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import type { ReviewCreateDTO } from '../../types/review.ts';
import ReviewForm from '../../components/forms/review/ReviewForm.tsx';
import { useAuth } from '../../context/useAuth.ts';
import { useEffect, useState } from 'react';
import type { RecipeDTO } from '../../types/recipe.ts';

function ReviewCreateView() {
  const navigate = useNavigate();
  const { recipeId } = useParams();
  const { user } = useAuth();
  const location = useLocation();
  const [recipeName, setRecipeName] = useState<string>('');

  useEffect(() => {
    if (!recipeId) return;

    let cancelled = false;

    (async () => {
      try {
        const res = await apiFetch<RecipeDTO | null>(`/api/recipes/${recipeId}`, 'GET');

        if (cancelled) return;

        if (res && res.recipeName) {
          setRecipeName(res.recipeName);
        } else {
          toast.error('Recipe not found.');
        }
      } catch (err) {
        if (cancelled) return;

        const message =
          err instanceof Error
            ? err.message
            : typeof err === 'object' && err && 'body' in err
              ? String((err as { body?: unknown }).body)
              : 'Failed to load recipe.';
        toast.error(message);
      }
    })();

    return () => {
      cancelled = true;
    };
  }, [recipeId]);

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
      <h1>Create Review for {recipeName || '...'}</h1>
      <ReviewForm onSubmit={handleSave} />
    </div>
  );
}

export default ReviewCreateView;
