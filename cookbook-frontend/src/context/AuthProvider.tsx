import { type ReactNode, useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';
import type { UserDTO } from '../types/user';

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserDTO | null>(null);
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    const savedToken = localStorage.getItem('token');

    try {
      if (savedUser && savedToken) {
        setUser(JSON.parse(savedUser));
        setToken(savedToken);
      }
    } catch (err) {
      console.error('Invalid user in localStorage. Clearing it.', err);
      localStorage.removeItem('user');
      setUser(null);
    }
  }, []);

  const login = (user: UserDTO, jwt: string) => {
    setUser(user);
    setToken(jwt);
    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('token', jwt);
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>{children}</AuthContext.Provider>
  );
}
