import { useForm } from 'react-hook-form';
import { useState } from 'react';

type ForgotPasswordFormData = {
  email: string;
};

function ForgotPasswordView() {
  const { register, handleSubmit, watch } = useForm<ForgotPasswordFormData>();
  const [message, setMessage] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [lastSubmittedEmail, setLastSubmittedEmail] = useState<string>('');

  const onSubmit = async (data: ForgotPasswordFormData) => {
    setIsSubmitting(true);
    try {
      const res = await fetch('/api/password-reset/request', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });

      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(errorText || 'Failed to send reset link');
      }

      setMessage('Check your email for a reset link.');
      setLastSubmittedEmail(data.email);
    } catch (err: unknown) {
      setMessage(err instanceof Error ? err.message : 'Unexpected error');
    } finally {
      setIsSubmitting(false);
    }
  };

  // Watch email changes and reset message if changed
  const emailValue = watch('email');
  const isButtonDisabled = isSubmitting || emailValue === lastSubmittedEmail;

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="form">
      <h2>Forgot Password</h2>
      <input
        type="email"
        placeholder="Enter your email"
        {...register('email', { required: true })}
      />
      <button type="submit" className="button button-blue" disabled={isButtonDisabled}>
        {isSubmitting ? 'Submitting...' : 'Submit'}
      </button>
      {message && <p>{message}</p>}
    </form>
  );
}

export default ForgotPasswordView;
