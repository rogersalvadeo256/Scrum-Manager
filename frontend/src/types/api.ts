export type AuthSession = {
  token: string;
  tokenType: string;
  userId: number;
  username: string;
};

export type AccountStatus = 'ACTIVE' | 'INACTIVE';
export type ProjectStatus = 'IN_PROGRESS' | 'FINISHED' | 'DELETED';
export type TaskStatus = 'TO_DO' | 'DOING' | 'DONE';
export type SprintStatus = 'DOING' | 'DONE';
export type RequestStatus = 'ON_HOLD' | 'ACCEPTED' | 'REFUSED' | 'REMOVED';

export type User = {
  id: number;
  username: string;
  email: string;
  status: AccountStatus;
  registrationDate: string;
  profileName: string;
};

export type Project = {
  id: number;
  name: string;
  description: string | null;
  creatorId: number;
  dateStart: string;
  status: ProjectStatus;
  type: string | null;
};

export type Task = {
  id: number;
  title: string;
  text: string | null;
  points: number;
  creatorId: number;
  executorId: number | null;
  status: TaskStatus;
  projectId: number;
  dateStart: string;
};

export type Sprint = {
  id: number;
  title: string;
  text: string | null;
  projectId: number;
  points: number;
  status: SprintStatus;
};

export type Friendship = {
  id: number;
  requestedById: number;
  requestedByUsername: string;
  receiverId: number;
  receiverUsername: string;
  status: RequestStatus;
  sentDate: string;
};

export type ProjectInvite = {
  id: number;
  invitedById: number;
  userId: number;
  projectId: number;
  inviteSentDate: string | null;
  inviteAnsweredDate: string | null;
  inviteStatus: RequestStatus;
  memberStatus?: RequestStatus | null;
  function?: string | null;
  permissions?: string | null;
  scrumMaster: boolean;
};

export type LoginPayload = {
  usernameOrEmail: string;
  password: string;
};

export type RegisterPayload = {
  username: string;
  email: string;
  password: string;
  securityQuestion: string;
  securityAnswer: string;
  profileName: string;
};

export type ActivateAccountPayload = {
  usernameOrEmail: string;
  securityQuestion: string;
  securityAnswer: string;
  newPassword: string;
};

export type ProjectPayload = {
  name: string;
  description: string;
  type: string;
};

export type TaskPayload = {
  title: string;
  text: string;
  points: number;
  executorId: number | null;
  status: TaskStatus;
};

export type SprintPayload = {
  title: string;
  text: string;
  points: number;
  status: SprintStatus;
};
