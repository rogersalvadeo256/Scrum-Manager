import { http } from './http';
import type { Sprint, SprintPayload, Task, TaskPayload } from '../types/api';

export async function getTasks(projectId: number) {
  const { data } = await http.get<Task[]>(`/api/projects/${projectId}/tasks`);
  return data;
}

export async function createTask(projectId: number, payload: TaskPayload) {
  const { data } = await http.post<Task>(`/api/projects/${projectId}/tasks`, payload);
  return data;
}

export async function updateTask(projectId: number, taskId: number, payload: TaskPayload) {
  const { data } = await http.put<Task>(`/api/projects/${projectId}/tasks/${taskId}`, payload);
  return data;
}

export async function deleteTask(projectId: number, taskId: number) {
  await http.delete(`/api/projects/${projectId}/tasks/${taskId}`);
}

export async function getSprints(projectId: number) {
  const { data } = await http.get<Sprint[]>(`/api/projects/${projectId}/sprints`);
  return data;
}

export async function createSprint(projectId: number, payload: SprintPayload) {
  const { data } = await http.post<Sprint>(`/api/projects/${projectId}/sprints`, payload);
  return data;
}

export async function updateSprint(projectId: number, sprintId: number, payload: SprintPayload) {
  const { data } = await http.put<Sprint>(`/api/projects/${projectId}/sprints/${sprintId}`, payload);
  return data;
}

export async function deleteSprint(projectId: number, sprintId: number) {
  await http.delete(`/api/projects/${projectId}/sprints/${sprintId}`);
}
