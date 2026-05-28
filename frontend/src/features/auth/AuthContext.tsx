import type { PropsWithChildren } from 'react';
import { createContext, useCallback, useContext, useMemo, useState } from 'react';
import { clearStoredSession, readStoredSession, writeStoredSession } from '../../lib/storage';
import type { AuthSession } from '../../types/api';

type AuthContextValue = {
  isAuthenticated: boolean;
  session: AuthSession | null;
  saveSession: (nextSession: AuthSession) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export function AuthProvider({ children }: PropsWithChildren) {
  const [session, setSession] = useState<AuthSession | null>(() => readStoredSession());

  const saveSession = useCallback((nextSession: AuthSession) => {
    writeStoredSession(nextSession);
    setSession(nextSession);
  }, []);

  const logout = useCallback(() => {
    clearStoredSession();
    setSession(null);
  }, []);

  const value = useMemo<AuthContextValue>(
    () => ({
      isAuthenticated: session !== null,
      session,
      saveSession,
      logout,
    }),
    [logout, saveSession, session],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }

  return context;
}
