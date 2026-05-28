import type { AuthSession } from '../types/api';

const SESSION_KEY = 'scrum-manager.session.v1';

function isValidSession(value: unknown): value is AuthSession {
  if (!value || typeof value !== 'object') {
    return false;
  }

  const candidate = value as Record<string, unknown>;

  return (
    typeof candidate.token === 'string' &&
    typeof candidate.tokenType === 'string' &&
    typeof candidate.userId === 'number' &&
    typeof candidate.username === 'string'
  );
}

export function readStoredSession() {
  try {
    const raw = window.sessionStorage.getItem(SESSION_KEY);

    if (!raw) {
      return null;
    }

    const parsed = JSON.parse(raw) as unknown;

    return isValidSession(parsed) ? parsed : null;
  } catch {
    return null;
  }
}

export function writeStoredSession(session: AuthSession) {
  window.sessionStorage.setItem(SESSION_KEY, JSON.stringify(session));
}

export function clearStoredSession() {
  window.sessionStorage.removeItem(SESSION_KEY);
}
