import { createContext } from 'react';
import type { AuthSession } from '../../types/api';

export type AuthContextValue = {
  isAuthenticated: boolean;
  session: AuthSession | null;
  saveSession: (nextSession: AuthSession) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextValue | undefined>(undefined);
