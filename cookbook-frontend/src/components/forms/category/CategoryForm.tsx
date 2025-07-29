import type { CategoryDTO } from '../../../types/category.ts';
import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import SaveButton from '../../buttons/SaveButton.tsx';
import CancelButton from '../../buttons/CancelButton.tsx';

type CategoryFormProps = {
  defaultValues?: Partial<CategoryDTO>;
  onSubmit: (data: Partial<CategoryDTO>) => void;
};

function CategoryForm({ defaultValues = {}, onSubmit }: CategoryFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<CategoryDTO>>({
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
        <label>Category Name</label>
        <input
          {...register('categoryName', {
            required: 'Category name is required.',
          })}
          type="text"
          />
        {errors.categoryName && <p className="error">{errors.categoryName.message}</p>}

        <SaveButton type="submit" />
        <CancelButton />
      </div>
    </form>
  )
}

export default CategoryForm;