import type { PropsWithChildren } from 'react';
import { useCallback, useMemo, useState } from 'react';
import { clearStoredSession, readStoredSession, writeStoredSession } from '../../lib/storage';
import type { AuthSession } from '../../types/api';
import { AuthContext } from './auth-context';

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

  const value = useMemo(
    () => ({
      isAuthenticated: session !== null,
      logout,
      saveSession,
      session,
    }),
    [logout, saveSession, session],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
