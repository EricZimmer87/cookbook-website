import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function RegisterView() {
  const [userName, setUserName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const passwordsMatch = confirmPassword.length > 0 && password === confirmPassword;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!passwordsMatch) {
      setMessage('Passwords do not match.');
      return;
    }

    const payload = { userName, userEmail, password };

    try {
      const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      const text = await response.text();

      if (response.ok) {
        setMessage('Account created successfully! Redirecting to login...');
        setTimeout(() => navigate('/login'), 2000);
      } else {
        setMessage(`Error: ${text}`);
      }
    } catch (err) {
      console.error(err);
      setMessage('An error occurred. Please try again.');
    }
  };

  return (
    <div>
      <h1>Create Account</h1>
      <form className="form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Username:</label>
          <input
            type="text"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={userEmail}
            onChange={(e) => setUserEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            minLength={6}
          />
        </div>
        <div className="form-group">
          <label>Confirm Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            minLength={6}
          />
          {confirmPassword.length > 0 && !passwordsMatch && (
            <p className="error">Passwords must match</p>
          )}
          {passwordsMatch && <p>Passwords match</p>}
        </div>
        <button type="submit" className="button button-blue" disabled={!passwordsMatch}>
          Register
        </button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default RegisterView;
