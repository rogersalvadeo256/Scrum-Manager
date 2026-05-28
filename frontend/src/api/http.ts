import axios from 'axios';
import { getApiBaseUrl } from '../lib/env';
import { readStoredSession } from '../lib/storage';

export const http = axios.create({
  baseURL: getApiBaseUrl(),
  timeout: 15000,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
});

http.interceptors.request.use((config) => {
  const session = readStoredSession();

  if (session?.token) {
    config.headers.Authorization = `${session.tokenType} ${session.token}`;
  }

  return config;
});
