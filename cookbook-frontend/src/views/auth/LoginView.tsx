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
        console.error('Login failed');
        alert('Invalid login');
        return;
      }

      const { user, token } = await response.json();
      login(user, token);

      navigate('/'); // redirect to home
    } catch (err) {
      console.error(err);
      alert('Invalid login');
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
