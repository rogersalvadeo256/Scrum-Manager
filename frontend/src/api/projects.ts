import { http } from './http';
import type { Project, ProjectInvite, ProjectPayload, RequestStatus } from '../types/api';

export async function getOwnedProjects() {
  const { data } = await http.get<Project[]>('/api/projects/mine');
  return data;
}

export async function getMemberProjects() {
  const { data } = await http.get<Project[]>('/api/projects/member');
  return data;
}

export async function createProject(payload: ProjectPayload) {
  const { data } = await http.post<Project>('/api/projects', payload);
  return data;
}

export async function updateProject(projectId: number, payload: ProjectPayload) {
  const { data } = await http.put<Project>(`/api/projects/${projectId}`, payload);
  return data;
}

export async function deleteProject(projectId: number) {
  await http.delete(`/api/projects/${projectId}`);
}

export async function getPendingProjectInvites() {
  const { data } = await http.get<ProjectInvite[]>('/api/projects/invites/pending');
  return data;
}

export async function answerProjectInvite(memberId: number, answer: Extract<RequestStatus, 'ACCEPTED' | 'REFUSED'>) {
  await http.patch(`/api/projects/invites/${memberId}`, { answer });
}

export async function inviteProjectMember(projectId: number, userId: number) {
  await http.post(`/api/projects/${projectId}/invite/${userId}`);
}
