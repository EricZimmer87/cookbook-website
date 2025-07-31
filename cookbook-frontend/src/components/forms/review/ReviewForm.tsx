import type { ReviewDTO } from '../../../types/review.ts';
import { useForm } from 'react-hook-form';
import { useNavigate, useLocation } from 'react-router-dom';
import SaveButton from '../../buttons/SaveButton.tsx';
import { useEffect } from 'react';
import CancelButton from '../../buttons/CancelButton.tsx';

type ReviewFormProps = {
  defaultValues?: Partial<ReviewDTO>;
  onSubmit: (data: Partial<ReviewDTO>) => Promise<void>;
};

function ReviewForm({ defaultValues = {}, onSubmit }: ReviewFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<ReviewDTO>>({
    defaultValues,
  });

  useEffect(() => {
    if (defaultValues && Object.keys(defaultValues).length > 0) {
      reset(defaultValues);
    }
  }, [defaultValues, reset]);

  const navigate = useNavigate();
  const location = useLocation();

  const handleFormSubmit = async (data: Partial<ReviewDTO>) => {
    await onSubmit(data);
    if (location.state?.from) {
      navigate(location.state.from);
    } else {
      navigate('/');
    }
  };

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)} className="form">
      <div className="form-group">
        <label>Score</label>
        <input
          {...register('score', {
            required: 'Score is required.',
            min: { value: 0, message: 'Score must be at least 0.' },
            max: { value: 5, message: 'Score must be at most 5.' }
          })}
          type="number"
        />
        {errors.score && <p className="error">{errors.score.message}</p>}
      </div>

      <div className="form-group">
        <label>Review Text</label>
        <input
          {...register('reviewText', {
            required: 'Review is required.',
          })}
          type="text"
        />
        {errors.reviewText && <p className="error">{errors.reviewText.message}</p>}
      </div>

      <SaveButton type="submit" />
      <CancelButton type="submit" />
    </form>
  );
}

export default ReviewForm;
