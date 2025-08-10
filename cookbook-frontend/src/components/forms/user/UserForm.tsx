import { useForm } from 'react-hook-form';
import type { RoleDTO, UserDTO } from '../../../types/user';
import SaveButton from '../../buttons/SaveButton.tsx';
import { useEffect } from 'react';
import CancelButton from '../../buttons/CancelButton.tsx';

type UserFormProps = {
  defaultValues?: Partial<UserDTO>; // pre-fill for edit
  onSubmit: (data: Partial<UserDTO>) => void; // callback for parent to handle POST/PUT
  roles: RoleDTO[]; // available roles to choose
};

function UserForm({ defaultValues = {}, onSubmit }: UserFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<Partial<UserDTO>>({
    defaultValues,
  });

  useEffect(() => {
    reset(defaultValues);
  }, [defaultValues, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <div className="form-group">
        <label>Username</label>
        <input
          {...register('userName', {
            required: 'Username is required',
          })}
          type="text"
        />
        {errors.userName && <p className="error">{errors.userName.message}</p>}
      </div>

      <div className="form-group">
        <label>Email</label>
        <input
          {...register('userEmail', {
            required: 'Email is required',
            pattern: {
              value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
              message: 'Invalid email address',
            },
          })}
          type="email"
        />
        {errors.userEmail && <p className="error">{errors.userEmail.message}</p>}
      </div>

      <SaveButton type="submit" />
      <CancelButton />
    </form>
  );
}

export default UserForm;
