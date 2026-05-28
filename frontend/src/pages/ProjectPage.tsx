import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { ArrowLeft, Plus, Search, UserPlus } from 'lucide-react';
import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getFriends } from '../api/friendships';
import { getMemberProjects, getOwnedProjects, inviteProjectMember } from '../api/projects';
import {
  createSprint,
  createTask,
  deleteSprint,
  deleteTask,
  getSprints,
  getTasks,
  updateSprint,
  updateTask,
} from '../api/tasks';
import { getUser, searchUsers } from '../api/users';
import { SprintFormModal } from '../components/forms/SprintFormModal';
import { TaskFormModal } from '../components/forms/TaskFormModal';
import { Button } from '../components/ui/Button';
import { Card } from '../components/ui/Card';
import { EmptyState } from '../components/ui/EmptyState';
import { Input } from '../components/ui/Input';
import { SectionHeading } from '../components/ui/SectionHeading';
import { StatusBadge } from '../components/ui/StatusBadge';
import { formatDate } from '../lib/format';
import { getErrorMessage } from '../lib/errors';
import { useAuth } from '../features/auth/useAuth';
import type { Sprint, SprintPayload, Task, TaskPayload, TaskStatus, User } from '../types/api';

type TaskModalState = { mode: 'create' | 'edit'; value: Task | null } | null;
type SprintModalState = { mode: 'create' | 'edit'; value: Sprint | null } | null;

const columns: { label: string; value: TaskStatus }[] = [
  { label: 'A fazer', value: 'TO_DO' },
  { label: 'Em andamento', value: 'DOING' },
  { label: 'Concluído', value: 'DONE' },
];

export function ProjectPage() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { projectId } = useParams();
  const { session } = useAuth();
  const [feedback, setFeedback] = useState<string | null>(null);
  const [taskModal, setTaskModal] = useState<TaskModalState>(null);
  const [sprintModal, setSprintModal] = useState<SprintModalState>(null);
  const [inviteTerm, setInviteTerm] = useState('');

  const numericProjectId = Number.parseInt(projectId ?? '', 10);

  const ownedProjectsQuery = useQuery({
    queryFn: getOwnedProjects,
    queryKey: ['projects', 'owned'],
  });

  const memberProjectsQuery = useQuery({
    queryFn: getMemberProjects,
    queryKey: ['projects', 'member'],
  });

  const currentUserQuery = useQuery({
    enabled: Boolean(session),
    queryFn: () => getUser(session!.userId),
    queryKey: ['user', session?.userId],
  });

  const friendsQuery = useQuery({
    queryFn: getFriends,
    queryKey: ['friends'],
  });

  const tasksQuery = useQuery({
    enabled: Number.isFinite(numericProjectId),
    queryFn: () => getTasks(numericProjectId),
    queryKey: ['projects', numericProjectId, 'tasks'],
  });

  const sprintsQuery = useQuery({
    enabled: Number.isFinite(numericProjectId),
    queryFn: () => getSprints(numericProjectId),
    queryKey: ['projects', numericProjectId, 'sprints'],
  });

  const inviteSearchQuery = useQuery({
    enabled: inviteTerm.trim().length >= 2,
    queryFn: () => searchUsers(inviteTerm.trim()),
    queryKey: ['users', 'invite-search', inviteTerm.trim()],
  });

  const projects = useMemo(
    () =>
      [...(ownedProjectsQuery.data ?? []), ...(memberProjectsQuery.data ?? [])].filter(
        (project, index, array) => array.findIndex((entry) => entry.id === project.id) === index,
      ),
    [memberProjectsQuery.data, ownedProjectsQuery.data],
  );

  const project = projects.find((entry) => entry.id === numericProjectId) ?? null;
  const isOwner = project?.creatorId === session?.userId;
  const currentUser = currentUserQuery.data;
  const assignableUsers = useMemo(() => {
    const source = [currentUser, ...(friendsQuery.data ?? [])].filter(Boolean) as User[];
    return source.filter((user, index, array) => array.findIndex((item) => item.id === user.id) === index);
  }, [currentUser, friendsQuery.data]);

  const inviteCandidates = useMemo(() => {
    const assignedIds = new Set(assignableUsers.map((user) => user.id));
    return (inviteSearchQuery.data ?? []).filter((user) => !assignedIds.has(user.id));
  }, [assignableUsers, inviteSearchQuery.data]);

  const taskMutation = useMutation({
    mutationFn: async (payload: { taskId?: number; values: TaskPayload }) => {
      if (payload.taskId) {
        return updateTask(numericProjectId, payload.taskId, payload.values);
      }

      return createTask(numericProjectId, payload.values);
    },
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setTaskModal(null);
      setFeedback('Tarefa salva com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'tasks'] });
    },
  });

  const sprintMutation = useMutation({
    mutationFn: async (payload: { sprintId?: number; values: SprintPayload }) => {
      if (payload.sprintId) {
        return updateSprint(numericProjectId, payload.sprintId, payload.values);
      }

      return createSprint(numericProjectId, payload.values);
    },
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setSprintModal(null);
      setFeedback('Sprint salvo com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'sprints'] });
    },
  });

  const deleteTaskMutation = useMutation({
    mutationFn: (taskId: number) => deleteTask(numericProjectId, taskId),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Tarefa removida com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'tasks'] });
    },
  });

  const deleteSprintMutation = useMutation({
    mutationFn: (sprintId: number) => deleteSprint(numericProjectId, sprintId),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Sprint removido com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'sprints'] });
    },
  });

  const inviteMutation = useMutation({
    mutationFn: (userId: number) => inviteProjectMember(numericProjectId, userId),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: () => setFeedback('Convite enviado com sucesso.'),
  });

  const tasksByStatus = useMemo(() => {
    const tasks = tasksQuery.data ?? [];
    return {
      DOING: tasks.filter((task) => task.status === 'DOING'),
      DONE: tasks.filter((task) => task.status === 'DONE'),
      TO_DO: tasks.filter((task) => task.status === 'TO_DO'),
    };
  }, [tasksQuery.data]);

  const isLoading =
    ownedProjectsQuery.isLoading ||
    memberProjectsQuery.isLoading ||
    tasksQuery.isLoading ||
    sprintsQuery.isLoading ||
    friendsQuery.isLoading ||
    currentUserQuery.isLoading;

  async function handleTaskSubmit(values: TaskPayload) {
    await taskMutation.mutateAsync({
      taskId: taskModal?.mode === 'edit' ? taskModal.value?.id : undefined,
      values,
    });
  }

  async function handleSprintSubmit(values: SprintPayload) {
    await sprintMutation.mutateAsync({
      sprintId: sprintModal?.mode === 'edit' ? sprintModal.value?.id : undefined,
      values,
    });
  }

  async function handleDeleteTask(taskId: number) {
    if (!window.confirm('Deseja remover esta tarefa?')) {
      return;
    }

    await deleteTaskMutation.mutateAsync(taskId);
  }

  async function handleDeleteSprint(sprintId: number) {
    if (!window.confirm('Deseja remover este sprint?')) {
      return;
    }

    await deleteSprintMutation.mutateAsync(sprintId);
  }

  if (!Number.isFinite(numericProjectId)) {
    return (
      <main className="mx-auto max-w-3xl px-4 py-8">
        <Card className="p-8 text-center text-slate-300">Projeto inválido.</Card>
      </main>
    );
  }

  return (
    <main className="mx-auto min-h-screen w-full max-w-7xl space-y-6 px-4 py-6 lg:px-8">
      <header className="surface flex flex-col gap-4 p-6 md:flex-row md:items-center md:justify-between">
        <div>
          <button className="mb-3 inline-flex items-center gap-2 text-sm text-slate-400 hover:text-white" onClick={() => navigate('/')} type="button">
            <ArrowLeft className="size-4" />
            Voltar ao dashboard
          </button>
          <h1 className="text-3xl font-semibold text-white">{project?.name ?? 'Carregando projeto...'}</h1>
          <p className="mt-2 max-w-2xl text-subtle">
            {project?.description?.trim() || 'Projeto sem descrição cadastrada.'}
          </p>
        </div>
        <div className="flex flex-wrap gap-3">
          <Button onClick={() => setTaskModal({ mode: 'create', value: null })}>
            <Plus className="size-4" />
            Nova tarefa
          </Button>
          <Button onClick={() => setSprintModal({ mode: 'create', value: null })} variant="secondary">
            <Plus className="size-4" />
            Novo sprint
          </Button>
        </div>
      </header>

      {feedback ? (
        <div className="rounded-2xl border border-brand-400/20 bg-brand-500/10 px-4 py-3 text-sm text-brand-50">
          {feedback}
        </div>
      ) : null}

      {isLoading ? <Card className="p-8 text-center text-slate-300">Carregando projeto...</Card> : null}

      {!isLoading && !project ? (
        <Card className="p-8">
          <EmptyState
            description="O backend ainda não possui endpoint dedicado para leitura pública de projetos, então a tela depende dos projetos associados à sua conta."
            title="Projeto não disponível para esta sessão"
          />
        </Card>
      ) : null}

      {!isLoading && project ? (
        <>
          <section className="grid gap-6 xl:grid-cols-[1.2fr_0.8fr]">
            <Card className="space-y-4">
              <SectionHeading
                description="Backlog organizado em colunas similares ao fluxo clássico do Scrum Manager."
                title="Board de tarefas"
              />
              <div className="grid gap-4 xl:grid-cols-3">
                {columns.map((column) => (
                  <div className="surface-muted space-y-3 p-4" key={column.value}>
                    <div className="flex items-center justify-between gap-3">
                      <p className="font-semibold text-white">{column.label}</p>
                      <span className="text-sm text-slate-400">{tasksByStatus[column.value].length}</span>
                    </div>
                    <div className="space-y-3">
                      {tasksByStatus[column.value].map((task) => (
                        <article className="rounded-2xl border border-white/10 bg-slate-950/60 p-4" key={task.id}>
                          <div className="space-y-3">
                            <div className="flex items-center justify-between gap-2">
                              <p className="font-medium text-white">{task.title}</p>
                              <StatusBadge status={task.status} />
                            </div>
                            <p className="text-sm text-slate-400">{task.text?.trim() || 'Sem descrição.'}</p>
                            <div className="text-xs uppercase tracking-[0.2em] text-slate-500">
                              {task.points} pts · executor {task.executorId ?? 'não definido'}
                            </div>
                            <div className="flex flex-wrap gap-2">
                              {task.creatorId === session?.userId ? (
                                <>
                                  <Button onClick={() => setTaskModal({ mode: 'edit', value: task })} variant="ghost">
                                    Editar
                                  </Button>
                                  <Button
                                    isLoading={deleteTaskMutation.isPending}
                                    onClick={() => handleDeleteTask(task.id)}
                                    variant="danger"
                                  >
                                    Remover
                                  </Button>
                                </>
                              ) : null}
                            </div>
                          </div>
                        </article>
                      ))}
                      {tasksByStatus[column.value].length === 0 ? (
                        <EmptyState
                          description="Adicione tarefas novas ou mova atividades para esta etapa."
                          title={`Sem tarefas em ${column.label.toLowerCase()}`}
                        />
                      ) : null}
                    </div>
                  </div>
                ))}
              </div>
            </Card>

            <div className="space-y-6">
              <Card className="space-y-4">
                <SectionHeading
                  description="Sprints disponíveis para o projeto atual."
                  title="Sprints"
                />
                <div className="space-y-3">
                  {(sprintsQuery.data ?? []).map((sprint) => (
                    <div className="surface-muted space-y-3 p-4" key={sprint.id}>
                      <div className="flex items-center justify-between gap-2">
                        <p className="font-medium text-white">{sprint.title}</p>
                        <StatusBadge status={sprint.status} />
                      </div>
                      <p className="text-sm text-slate-400">{sprint.text?.trim() || 'Sem descrição.'}</p>
                      <p className="text-xs uppercase tracking-[0.2em] text-slate-500">{sprint.points} pts</p>
                      <div className="flex flex-wrap gap-2">
                        <Button onClick={() => setSprintModal({ mode: 'edit', value: sprint })} variant="ghost">
                          Editar
                        </Button>
                        <Button
                          isLoading={deleteSprintMutation.isPending}
                          onClick={() => handleDeleteSprint(sprint.id)}
                          variant="danger"
                        >
                          Remover
                        </Button>
                      </div>
                    </div>
                  ))}
                  {(sprintsQuery.data ?? []).length === 0 ? (
                    <EmptyState
                      description="Crie um sprint para começar a planejar entregas."
                      title="Nenhum sprint cadastrado"
                    />
                  ) : null}
                </div>
              </Card>

              <Card className="space-y-4">
                <SectionHeading
                  description="Resumo rápido do projeto."
                  title="Detalhes"
                />
                <div className="space-y-3 text-sm text-slate-300">
                  <p>
                    <span className="text-slate-500">Tipo:</span> {project.type?.trim() || 'Não informado'}
                  </p>
                  <p>
                    <span className="text-slate-500">Início:</span> {formatDate(project.dateStart)}
                  </p>
                  <p>
                    <span className="text-slate-500">Tarefas:</span> {(tasksQuery.data ?? []).length}
                  </p>
                  <p>
                    <span className="text-slate-500">Sprints:</span> {(sprintsQuery.data ?? []).length}
                  </p>
                </div>
              </Card>
            </div>
          </section>

          {isOwner ? (
            <Card className="space-y-4">
              <SectionHeading
                description="Convide colaboradores com base na busca global de usuários."
                title="Convidar pessoas"
              />
              <div className="relative max-w-xl">
                <Search className="pointer-events-none absolute left-4 top-1/2 size-4 -translate-y-1/2 text-slate-500" />
                <Input
                  className="pl-11"
                  onChange={(event) => setInviteTerm(event.target.value)}
                  placeholder="Buscar usuário por nome ou username"
                  value={inviteTerm}
                />
              </div>
              <div className="grid gap-3 md:grid-cols-2">
                {inviteCandidates.map((user) => (
                  <div className="surface-muted flex flex-col gap-3 p-4 md:flex-row md:items-center md:justify-between" key={user.id}>
                    <div>
                      <p className="font-medium text-white">{user.profileName}</p>
                      <p className="text-subtle">@{user.username}</p>
                    </div>
                    <Button isLoading={inviteMutation.isPending} onClick={() => inviteMutation.mutate(user.id)}>
                      <UserPlus className="size-4" />
                      Convidar
                    </Button>
                  </div>
                ))}
                {inviteTerm.trim().length >= 2 && !inviteSearchQuery.isLoading && inviteCandidates.length === 0 ? (
                  <EmptyState
                    description="Nenhum usuário elegível apareceu para este termo."
                    title="Sem resultados para convite"
                  />
                ) : null}
              </div>
            </Card>
          ) : null}
        </>
      ) : null}

      {taskModal ? (
        <TaskFormModal
          initialValue={taskModal.value}
          isSaving={taskMutation.isPending}
          members={assignableUsers}
          onClose={() => setTaskModal(null)}
          onSubmit={handleTaskSubmit}
        />
      ) : null}

      {sprintModal ? (
        <SprintFormModal
          initialValue={sprintModal.value}
          isSaving={sprintMutation.isPending}
          onClose={() => setSprintModal(null)}
          onSubmit={handleSprintSubmit}
        />
      ) : null}
    </main>
  );
}
