import { http } from './http';
import type { ApiKey, ChangePasswordPayload, CreateApiKeyPayload, UpdateProfilePayload, User, UserSettings } from '../types/api';

export async function getUser(userId: number) {
  const { data } = await http.get<User>(`/api/users/${userId}`);
  return data;
}

export async function searchUsers(name: string) {
  const { data } = await http.get<User[]>('/api/users/search', {
    params: { name },
  });
  return data;
}

export async function updateProfilePhoto(userId: number, file: File) {
  const bytes = await file.arrayBuffer();

  const { data } = await http.patch<User>(`/api/users/${userId}/photo`, bytes, {
    headers: {
      'Content-Type': 'application/octet-stream',
    },
  });

  return data;
}

export async function getMySettings() {
  const { data } = await http.get<UserSettings>('/api/users/me');
  return data;
}

export async function updateProfile(payload: UpdateProfilePayload) {
  const { data } = await http.patch<UserSettings>('/api/users/me/profile', payload);
  return data;
}

export async function changePassword(payload: ChangePasswordPayload) {
  await http.patch('/api/users/me/password', payload);
}

export async function listApiKeys() {
  const { data } = await http.get<ApiKey[]>('/api/users/me/api-keys');
  return data;
}

export async function createApiKey(payload: CreateApiKeyPayload) {
  const { data } = await http.post<ApiKey>('/api/users/me/api-keys', payload);
  return data;
}

export async function deleteApiKey(keyId: number) {
  await http.delete(`/api/users/me/api-keys/${keyId}`);
}
