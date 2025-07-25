import type { ReviewDTO } from '../../../types/review.ts';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import SaveButton from '../../buttons/SaveButton.tsx';
import Button from '../../buttons/Button.tsx';
import { useEffect } from 'react';

type ReviewFormProps = {
  defaultValues?: Partial<ReviewDTO>;
  onSubmit: (data: Partial<ReviewDTO>) => void;
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

  const handleCancel = () => {
    navigate(-1);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <div className="form-group">
        <label>Score</label>
        <input
          {...register('score', {
            required: 'Score is required.',
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
      <Button className="button" onClick={handleCancel}>
        Cancel
      </Button>
    </form>
  );
}

export default ReviewForm;
