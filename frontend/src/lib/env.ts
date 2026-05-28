const FALLBACK_API_URL = 'http://localhost:8080';

export function getApiBaseUrl() {
  return (import.meta.env.VITE_API_URL ?? FALLBACK_API_URL).trim();
}
