import type { TagDTO } from '../../../types/tag.ts';
import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import SaveButton from '../../buttons/SaveButton.tsx';
import CancelButton from '../../buttons/CancelButton.tsx';

type TagFormProps = {
  defaultValues?: Partial<TagDTO>;
  onSubmit: (data: Partial<TagDTO>) => void;
};

function TagForm({ defaultValues = {}, onSubmit }: TagFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<TagDTO>>({
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
        <label>Tag Name</label>
        <input
          {...register('tagName', {
            required: 'Tag name is required.',
          })}
          type="text"
        />
        {errors.tagName && <p className="error">{errors.tagName.message}</p>}
      </div>

      <SaveButton type="submit" />
      <CancelButton />
    </form>
  );
}

export default TagForm;
