import type { DifficultyLevelDTO } from '../../../types/difficulty-level.ts';
import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import SaveButton from '../../buttons/SaveButton.tsx';
import CancelButton from '../../buttons/CancelButton.tsx';

type DifficultyLevelFormProps = {
  defaultValues?: Partial<DifficultyLevelDTO>;
  onSubmit: (data: Partial<DifficultyLevelDTO>) => void;
};

function DifficultyLevelForm({ defaultValues = {}, onSubmit }: DifficultyLevelFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<DifficultyLevelDTO>>({
    defaultValues,
  });

  useEffect(() => {
    if (defaultValues && Object.keys(defaultValues).length > 0) {
      reset(defaultValues);
    }
  }, [defaultValues, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <div className="form-group">
        <label>Difficulty Level Name</label>
        <input
          {...register('difficultyLevelName', {
          required: 'Difficulty level name is required',
          })}
        type="text"
        />
        {errors.difficultyLevelName && <p className="error">{errors.difficultyLevelName.message}</p>}

        <SaveButton type="submit" />
        <CancelButton />
      </div>
    </form>
  )
}

export default DifficultyLevelForm;