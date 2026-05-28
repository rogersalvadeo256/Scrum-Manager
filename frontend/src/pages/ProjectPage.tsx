import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { ArrowLeft, ArrowRight, BarChart3, CheckCircle2, CircleDashed, Plus, Search, TrendingUp, UserPlus } from 'lucide-react';
import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getFriends } from '../api/friendships';
import { getMemberProjects, getOwnedProjects, getProjectMetrics, inviteProjectMember } from '../api/projects';
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

const taskTransitions: Record<TaskStatus, { next?: TaskStatus; previous?: TaskStatus }> = {
  DOING: { next: 'DONE', previous: 'TO_DO' },
  DONE: { previous: 'DOING' },
  TO_DO: { next: 'DOING' },
};

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

  const metricsQuery = useQuery({
    enabled: Number.isFinite(numericProjectId),
    queryFn: () => getProjectMetrics(numericProjectId),
    queryKey: ['projects', numericProjectId, 'metrics'],
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

  const quickTaskFlowMutation = useMutation({
    mutationFn: ({ status, task }: { status: TaskStatus; task: Task }) =>
      updateTask(numericProjectId, task.id, {
        executorId: task.executorId,
        points: task.points,
        status,
        text: task.text ?? '',
        title: task.title,
      }),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Tarefa movida com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'tasks'] });
    },
  });

  const quickSprintFlowMutation = useMutation({
    mutationFn: (sprint: Sprint) =>
      updateSprint(numericProjectId, sprint.id, {
        points: sprint.points,
        status: sprint.status === 'DOING' ? 'DONE' : 'DOING',
        text: sprint.text ?? '',
        title: sprint.title,
      }),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Status do sprint atualizado.');
      await queryClient.invalidateQueries({ queryKey: ['projects', numericProjectId, 'sprints'] });
    },
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

  const tasks = tasksQuery.data ?? [];
  const sprints = sprintsQuery.data ?? [];
  const metrics = metricsQuery.data ?? null;
  const totalTaskPoints = tasks.reduce((total, task) => total + task.points, 0);
  const doneTaskPoints = tasksByStatus.DONE.reduce((total, task) => total + task.points, 0);
  const sprintPoints = sprints.reduce((total, sprint) => total + sprint.points, 0);
  const doneSprintPoints = sprints
    .filter((sprint) => sprint.status === 'DONE')
    .reduce((total, sprint) => total + sprint.points, 0);
  const currentSprint = sprints.find((sprint) => sprint.status === 'DOING') ?? null;
  const taskCompletionRate = tasks.length === 0 ? 0 : Math.round((tasksByStatus.DONE.length / tasks.length) * 100);
  const sprintCompletionRate = sprints.length === 0 ? 0 : Math.round((sprints.filter((sprint) => sprint.status === 'DONE').length / sprints.length) * 100);

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

  async function handleMoveTask(task: Task, status: TaskStatus) {
    if (task.status === status) {
      return;
    }

    await quickTaskFlowMutation.mutateAsync({ status, task });
  }

  async function handleToggleSprintStatus(sprint: Sprint) {
    await quickSprintFlowMutation.mutateAsync(sprint);
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

      {!isLoading && project ? (
        <>
          <section className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
          <Card className="space-y-2 p-5">
            <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Tarefas concluídas</p>
            <p className="text-3xl font-semibold text-white">{taskCompletionRate}%</p>
            <p className="text-subtle">{tasksByStatus.DONE.length} de {tasks.length} tarefas</p>
          </Card>
          <Card className="space-y-2 p-5">
            <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Pontos entregues</p>
            <p className="text-3xl font-semibold text-white">{doneTaskPoints} / {totalTaskPoints}</p>
            <p className="text-subtle">Capacidade evoluindo por entrega</p>
          </Card>
          <Card className="space-y-2 p-5">
            <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Sprints finalizados</p>
            <p className="text-3xl font-semibold text-white">{sprintCompletionRate}%</p>
            <p className="text-subtle">{sprints.filter((sprint) => sprint.status === 'DONE').length} de {sprints.length} sprints</p>
          </Card>
          <Card className="space-y-2 p-5">
            <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Pontos de sprint</p>
            <p className="text-3xl font-semibold text-white">{doneSprintPoints} / {sprintPoints}</p>
            <p className="text-subtle">Acompanhe evolução da cadência</p>
          </Card>
        </section>

        {metrics ? (
          <section className="space-y-4">
            <div className="flex items-center gap-3">
              <BarChart3 className="size-5 text-brand-200" />
              <div>
                <h2 className="text-lg font-semibold text-white">Dashboard do Scrum Master</h2>
                <p className="text-sm text-slate-400 capitalize">{metrics.currentMonth}</p>
              </div>
            </div>

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
              <Card className="space-y-2 p-5">
                <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Tarefas no mês</p>
                <p className="text-3xl font-semibold text-white">{metrics.tasksThisMonth}</p>
                <p className="text-subtle">{metrics.doneThisMonth} concluídas este mês</p>
              </Card>
              <Card className="space-y-2 p-5">
                <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Velocidade (mês)</p>
                <p className="text-3xl font-semibold text-white">{metrics.velocityThisMonth} pts</p>
                <p className="text-subtle">Pontos entregues no mês atual</p>
              </Card>
              <Card className="space-y-2 p-5">
                <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Membros ativos</p>
                <p className="text-3xl font-semibold text-white">{metrics.membersCount}</p>
                <p className="text-subtle">Pessoas no projeto</p>
              </Card>
              <Card className="space-y-2 p-5">
                <p className="text-xs uppercase tracking-[0.2em] text-brand-200">Sprints ativos</p>
                <p className="text-3xl font-semibold text-white">{metrics.activeSprints}</p>
                <p className="text-subtle">{metrics.completedSprints} de {metrics.totalSprints} finalizados</p>
              </Card>
            </div>

            <div className="grid gap-6 xl:grid-cols-2">
              <Card className="space-y-4">
                <SectionHeading
                  description="Distribuição atual das tarefas por status."
                  title="Status das tarefas"
                />
                <div className="space-y-3">
                  {[
                    { color: 'bg-slate-500', count: metrics.todoCount, label: 'A fazer' },
                    { color: 'bg-brand-400', count: metrics.doingCount, label: 'Em andamento' },
                    { color: 'bg-emerald-400', count: metrics.doneCount, label: 'Concluído' },
                  ].map((item) => (
                    <div className="space-y-1" key={item.label}>
                      <div className="flex justify-between text-sm">
                        <span className="text-slate-300">{item.label}</span>
                        <span className="text-white font-semibold">{item.count}</span>
                      </div>
                      <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
                        <div
                          className={`h-full rounded-full ${item.color} transition-all`}
                          style={{ width: metrics.totalTasks === 0 ? '0%' : `${Math.round((item.count / metrics.totalTasks) * 100)}%` }}
                        />
                      </div>
                    </div>
                  ))}
                  <div className="pt-2">
                    <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
                      <div
                        className="h-full rounded-full bg-emerald-400 transition-all"
                        style={{ width: `${metrics.taskCompletionRate}%` }}
                      />
                    </div>
                    <p className="mt-1 text-xs text-slate-500 uppercase tracking-[0.2em]">
                      {metrics.taskCompletionRate}% do total concluído
                    </p>
                  </div>
                </div>
              </Card>

              <Card className="space-y-4">
                <SectionHeading
                  description="Resumo consolidado de pontos e entregas."
                  title="Pontuação geral"
                />
                <div className="space-y-4">
                  <div className="space-y-2">
                    <div className="flex justify-between text-sm">
                      <span className="text-slate-400">Pontos entregues</span>
                      <span className="text-white font-semibold">{metrics.completedPoints} / {metrics.totalPoints}</span>
                    </div>
                    <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
                      <div
                        className="h-full rounded-full bg-brand-400 transition-all"
                        style={{ width: metrics.totalPoints === 0 ? '0%' : `${Math.round((metrics.completedPoints / metrics.totalPoints) * 100)}%` }}
                      />
                    </div>
                  </div>
                  <div className="space-y-2">
                    <div className="flex justify-between text-sm">
                      <span className="text-slate-400">Sprints finalizados</span>
                      <span className="text-white font-semibold">{metrics.completedSprints} / {metrics.totalSprints}</span>
                    </div>
                    <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
                      <div
                        className="h-full rounded-full bg-emerald-400 transition-all"
                        style={{ width: `${metrics.sprintCompletionRate}%` }}
                      />
                    </div>
                  </div>
                  <div className="grid grid-cols-2 gap-3 pt-2 text-sm">
                    <div className="surface-muted p-3 text-center">
                      <p className="text-2xl font-semibold text-white">{metrics.velocityThisMonth}</p>
                      <p className="text-slate-500 text-xs uppercase tracking-[0.2em] mt-1">pts este mês</p>
                    </div>
                    <div className="surface-muted p-3 text-center">
                      <p className="text-2xl font-semibold text-white">{metrics.doneThisMonth}</p>
                      <p className="text-slate-500 text-xs uppercase tracking-[0.2em] mt-1">tarefas no mês</p>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
          </section>
        ) : null}
      </>
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
                description="Backlog com progressão rápida para manter o fluxo de entrega contínuo."
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
                        <article className="rounded-2xl border border-white/10 bg-slate-950/60 p-4 shadow-lg shadow-slate-950/30 transition hover:border-brand-400/40" key={task.id}>
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
                              {taskTransitions[task.status].previous ? (
                                <Button
                                  isLoading={quickTaskFlowMutation.isPending}
                                  onClick={() => handleMoveTask(task, taskTransitions[task.status].previous!)}
                                  variant="secondary"
                                >
                                  <ArrowLeft className="size-4" />
                                  Voltar etapa
                                </Button>
                              ) : null}
                              {taskTransitions[task.status].next ? (
                                <Button
                                  isLoading={quickTaskFlowMutation.isPending}
                                  onClick={() => handleMoveTask(task, taskTransitions[task.status].next!)}
                                  variant="secondary"
                                >
                                  Avançar etapa
                                  <ArrowRight className="size-4" />
                                </Button>
                              ) : null}
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
                  description="Ciclo ativo e histórico de sprints com atualização de status em um clique."
                  title="Fluxo de sprint"
                />
                {currentSprint ? (
                  <div className="surface-muted flex flex-col gap-3 p-4">
                    <div className="flex items-center justify-between gap-3">
                      <p className="font-medium text-white">Sprint ativo: {currentSprint.title}</p>
                      <StatusBadge status={currentSprint.status} />
                    </div>
                    <p className="text-subtle">{currentSprint.text?.trim() || 'Sprint ativo sem descrição.'}</p>
                    <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
                      <div className="h-full rounded-full bg-brand-400" style={{ width: `${taskCompletionRate}%` }} />
                    </div>
                    <p className="text-xs uppercase tracking-[0.2em] text-slate-500">
                      progresso das tarefas do projeto · {taskCompletionRate}%
                    </p>
                  </div>
                ) : (
                  <div className="surface-muted flex items-center gap-3 p-4 text-sm text-slate-300">
                    <CircleDashed className="size-4 text-brand-200" />
                    Nenhum sprint em andamento no momento.
                  </div>
                )}
                <div className="space-y-3">
                  {sprints.map((sprint) => (
                    <div className="surface-muted space-y-3 p-4" key={sprint.id}>
                      <div className="flex items-center justify-between gap-2">
                        <p className="font-medium text-white">{sprint.title}</p>
                        <StatusBadge status={sprint.status} />
                      </div>
                      <p className="text-sm text-slate-400">{sprint.text?.trim() || 'Sem descrição.'}</p>
                      <p className="text-xs uppercase tracking-[0.2em] text-slate-500">{sprint.points} pts</p>
                      <div className="flex flex-wrap gap-2">
                        <Button
                          isLoading={quickSprintFlowMutation.isPending}
                          onClick={() => handleToggleSprintStatus(sprint)}
                          variant="secondary"
                        >
                          {sprint.status === 'DOING' ? (
                            <>
                              <CheckCircle2 className="size-4" />
                              Marcar como concluído
                            </>
                          ) : (
                            <>
                              <TrendingUp className="size-4" />
                              Reabrir sprint
                            </>
                          )}
                        </Button>
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
                  {sprints.length === 0 ? (
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
                  <p>
                    <span className="text-slate-500">Sprint ativo:</span> {currentSprint?.title ?? 'Não há sprint em andamento'}
                  </p>
                  <p>
                    <span className="text-slate-500">Ritmo:</span> {taskCompletionRate}% das tarefas concluídas
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
