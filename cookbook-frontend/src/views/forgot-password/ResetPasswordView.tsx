import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link, useSearchParams } from 'react-router-dom';

type ResetPasswordFormData = {
  newPassword: string;
};

function ResetPasswordView() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<ResetPasswordFormData>();

  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const [status, setStatus] = useState<'validating' | 'valid' | 'error' | 'done'>('validating');

  useEffect(() => {
    const validateToken = async () => {
      try {
        const res = await fetch(`/api/password-reset/validate?token=${token}`);
        if (!res.ok) throw new Error('Token validation failed.');
        setStatus('valid');
      } catch (error) {
        console.error(error);
        setStatus('error');
      }
    };

    if (token) {
      validateToken().catch(() => {});
    } else {
      setStatus('error');
    }
  }, [token]);

  const onSubmit = async (data: ResetPasswordFormData) => {
    try {
      const res = await fetch('/api/password-reset/reset', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          token,
          newPassword: data.newPassword,
        }),
      });

      if (!res.ok) throw new Error();
      setStatus('done');
    } catch {
      setStatus('error');
    }
  };

  if (status === 'validating') return <p>Validating token...</p>;
  if (status === 'error') return <p>Invalid or expired token.</p>;
  if (status === 'done')
    return (
      <div>
        <p>Password successfully reset!</p>
        <Link to="/login">Click here to login.</Link>
      </div>
    );

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <h2>Reset Password</h2>
      <input
        type="password"
        placeholder="New Password"
        {...register('newPassword', {
          required: 'Password is required',
          minLength: {
            value: 6,
            message: 'Password must be at least 6 characters long',
          },
        })}
      />
      {errors.newPassword && <p className="error">{errors.newPassword.message}</p>}

      <button type="submit" disabled={isSubmitting} className="button button-blue">
        {isSubmitting ? 'Resetting...' : 'Reset Password'}
      </button>
    </form>
  );
}

export default ResetPasswordView;
