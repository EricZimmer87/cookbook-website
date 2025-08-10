import { useForm } from 'react-hook-form';
import { useAuth } from '../../context/useAuth';
import Button from '../../components/buttons/Button.tsx';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

type FormData = {
  currentPassword: string;
  newPassword: string;
  confirmNewPassword: string;
};

function ChangePasswordView() {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [success, setSuccess] = useState(false);

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors, isSubmitting },
    setError,
    reset,
  } = useForm<FormData>();

  const newPwd = watch('newPassword', '');
  const confirmNewPwd = watch('confirmNewPassword', '');
  const passwordsMismatch = confirmNewPwd.length > 0 && newPwd !== confirmNewPwd;

  const onSubmit = async (data: FormData) => {
    if (data.newPassword !== data.confirmNewPassword) {
      setError('confirmNewPassword', { message: 'Passwords do not match' });
      return;
    }

    try {
      const res = await fetch('/api/auth/change-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...(token ? { Authorization: `Bearer ${token}` } : {}),
        },
        body: JSON.stringify({
          currentPassword: data.currentPassword,
          newPassword: data.newPassword,
        }),
      });

      const text = await res.text();

      if (!res.ok) {
        if (text.toLowerCase().includes('current password')) {
          setError('currentPassword', { message: 'Current password is incorrect.' });
        } else {
          setError('newPassword', { message: text || 'Failed to change password' });
        }
        return;
      }

      reset();
      setSuccess(true);
    } catch {
      setError('root', { message: 'Error changing password. Please try again.' });
    }
  };

  if (success) {
    return (
      <div>
        <p>Password changed successfully!</p>
        <Button onClick={() => navigate(-1)}>Go back</Button>
      </div>
    );
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <h2>Change Password</h2>

      <input
        type="password"
        placeholder="Current password"
        {...register('currentPassword', { required: 'Current password is required' })}
      />
      {errors.currentPassword && <p className="error">{errors.currentPassword.message}</p>}

      <input
        type="password"
        placeholder="New password"
        {...register('newPassword', {
          required: 'New password is required',
          minLength: { value: 6, message: 'At least 6 characters' },
        })}
      />
      {errors.newPassword && <p className="error">{errors.newPassword.message}</p>}

      <input
        type="password"
        placeholder="Confirm new password"
        {...register('confirmNewPassword', {
          required: 'Please confirm your new password',
          validate: (v) => v === newPwd || 'Passwords do not match',
        })}
      />
      {(errors.confirmNewPassword || passwordsMismatch) && (
        <p className="error">{errors.confirmNewPassword?.message || 'Passwords do not match'}</p>
      )}

      {errors.root && <p className="error">{errors.root.message}</p>}

      <button
        type="submit"
        className="button button-blue"
        disabled={isSubmitting || passwordsMismatch}
      >
        {isSubmitting ? 'Saving...' : 'Change Password'}
      </button>
    </form>
  );
}

export default ChangePasswordView;
