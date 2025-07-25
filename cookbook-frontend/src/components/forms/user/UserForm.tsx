import { useForm } from 'react-hook-form';
import type { RoleDTO, UserDTO } from '../../../types/user';
import { useNavigate } from 'react-router-dom';
import Button from '../../buttons/Button.tsx';
import SaveButton from '../../buttons/SaveButton.tsx';
import { useEffect } from 'react';

type UserFormProps = {
  defaultValues?: Partial<UserDTO>; // pre-fill for edit
  onSubmit: (data: Partial<UserDTO>) => void; // callback for parent to handle POST/PUT
  roles: RoleDTO[]; // available roles to choose
};

function UserForm({ defaultValues = {}, onSubmit, roles }: UserFormProps) {
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

  const navigate = useNavigate();

  const handleCancel = () => {
    navigate(-1);
  };

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

      <div className="form-group">
        <label>Role</label>
        <select {...register('role.roleId', { required: true })}>
          <option value="">-- Select Role --</option>
          {roles.map((role) => (
            <option key={role.roleId} value={role.roleId}>
              {role.roleId} {role.roleName}
            </option>
          ))}
        </select>
        {errors.role?.roleId && <p className="error">Role is required</p>}
      </div>

      <SaveButton type="submit" />
      <Button className="button" onClick={handleCancel}>
        Cancel
      </Button>
    </form>
  );
}

export default UserForm;
