import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import type { RoleDTO } from '../../../types/user';
import SaveButton from '../../buttons/SaveButton';
import CancelButton from '../../buttons/CancelButton';

export type RoleFormData = { roleId: number };

type RoleFormProps = {
  defaultValues?: RoleFormData;
  onSubmit: (data: RoleFormData) => void;
  roles: RoleDTO[];
};

function RoleForm({ defaultValues, onSubmit, roles }: RoleFormProps) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<RoleFormData>({
    defaultValues: defaultValues ?? { roleId: 0 },
  });

  useEffect(() => {
    if (defaultValues) reset(defaultValues);
  }, [defaultValues, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <div className="form-group">
        <label>Role</label>
        <select
          {...register('roleId', { required: true, valueAsNumber: true })}
          // Ensure a number is sent, not a string
        >
          <option value="">-- Select Role --</option>
          {roles.map((role) => (
            <option key={role.roleId} value={role.roleId}>
              {role.roleId} {role.roleName}
            </option>
          ))}
        </select>
        {errors.roleId && <p className="error">Role is required</p>}
      </div>

      <SaveButton type="submit" />
      <CancelButton />
      {isSubmitting && <p>Saving…</p>}
    </form>
  );
}

export default RoleForm;
