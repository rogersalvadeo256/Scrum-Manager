import { http } from './http';
import type { Friendship, RequestStatus, User } from '../types/api';

export async function getFriends() {
  const { data } = await http.get<User[]>('/api/friendships');
  return data;
}

export async function getPendingFriendRequests() {
  const { data } = await http.get<Friendship[]>('/api/friendships/pending');
  return data;
}

export async function sendFriendRequest(userId: number) {
  await http.post(`/api/friendships/request/${userId}`);
}

export async function answerFriendRequest(
  friendshipId: number,
  answer: Extract<RequestStatus, 'ACCEPTED' | 'REFUSED'>,
) {
  await http.patch(`/api/friendships/${friendshipId}/answer`, { answer });
}

export async function removeFriend(friendshipId: number) {
  await http.delete(`/api/friendships/${friendshipId}`);
}
