import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import Button from '../../components/buttons/Button.tsx';

function LoginView() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userEmail: email, password }),
      });

      if (!response.ok) {
        // Try to read the JSON body for a nicer message
        let msg = 'Invalid email or password.';
        try {
          const err = await response.json();
          if (response.status === 403 && err?.code === 'ACCOUNT_BANNED') {
            msg = err.message || 'Your account has been banned.';
          }
        } catch {
          // ignore parse errors; keep default msg
        }
        alert(msg);
        return;
      }

      const { user, token } = await response.json();

      // Make sure this matches what the rest of your app reads
      localStorage.setItem('accessToken', token);

      // keep your existing auth context call if needed
      login(user, token);

      navigate('/'); // redirect to home
    } catch (err) {
      console.error(err);
      alert('Invalid email or password.');
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h2>Login</h2>
      <div className="form-group">
        <label>Email:</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      </div>
      <div className="form-group">
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <Button type="submit" className="button-blue">
        Login
      </Button>
      <br />
      <br />
      <Link to="/forgot-password">Forgot your password?</Link>
    </form>
  );
}

export default LoginView;
