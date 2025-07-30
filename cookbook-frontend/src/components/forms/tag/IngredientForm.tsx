import type { IngredientDTO } from '../../../types/ingredient.ts';
import { useForm } from 'react-hook-form';
import { useEffect } from 'react';
import SaveButton from '../../buttons/SaveButton.tsx';
import CancelButton from '../../buttons/CancelButton.tsx';

type IngredientFormProps = {
  defaultValues?: Partial<IngredientDTO>;
  onSubmit: (data: Partial<IngredientDTO>) => void;
};

function IngredientForm({ defaultValues = {}, onSubmit }: IngredientFormProps) {
  const{
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<IngredientDTO>>({
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
        <label>Ingredient Name</label>
        <input
          {...register('ingredientName', {
            required: 'Ingredient name is required.',
          })}
          type="text"
          />
        {errors.ingredientName && <p className="error">{errors.ingredientName.message}</p>}
      </div>

      <SaveButton type="submit"></SaveButton>
      <CancelButton />
    </form>
  )
}

export default IngredientForm;