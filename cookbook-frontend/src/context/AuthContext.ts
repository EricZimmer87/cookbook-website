import { createContext } from 'react';
import type { UserDTO } from '../types/user';

type AuthContextType = {
  user: UserDTO | null;
  token: string | null;
  login: (user: UserDTO, token: string) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
