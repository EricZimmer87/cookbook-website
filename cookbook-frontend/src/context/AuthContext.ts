import { createContext } from 'react';

type AuthContextType = {
  user: string | null;
  token: string | null;
  login: (user: string, token: string) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
