import { http } from './http';
import type { User } from '../types/api';

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
