import { createContext } from 'react';

export type User = {
  userId: number;
  userName: string;
  userEmail: string;
};

type AuthContextType = {
  user: User | null;
  token: string | null;
  login: (user: User, token: string) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
