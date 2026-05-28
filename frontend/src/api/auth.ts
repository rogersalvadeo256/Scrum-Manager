import { http } from './http';
import type {
  ActivateAccountPayload,
  AuthSession,
  LoginPayload,
  RegisterPayload,
  User,
} from '../types/api';

export async function login(payload: LoginPayload) {
  const { data } = await http.post<AuthSession>('/api/auth/login', payload);
  return data;
}

export async function register(payload: RegisterPayload) {
  const { data } = await http.post<User>('/api/auth/register', payload);
  return data;
}

export async function activateAccount(payload: ActivateAccountPayload) {
  await http.post('/api/auth/activate', payload);
}
