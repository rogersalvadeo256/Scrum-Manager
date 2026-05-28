import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  FolderKanban,
  LogOut,
  Plus,
  Search,
  Shield,
  UserCircle2,
  UserPlus,
  Users,
} from 'lucide-react';
import type { ReactNode } from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { answerFriendRequest, getFriends, getPendingFriendRequests, sendFriendRequest } from '../api/friendships';
import {
  answerProjectInvite,
  createProject,
  deleteProject,
  getMemberProjects,
  getOwnedProjects,
  getPendingProjectInvites,
  updateProject,
} from '../api/projects';
import { getUser, searchUsers, updateProfilePhoto } from '../api/users';
import { ProjectFormModal } from '../components/forms/ProjectFormModal';
import { Button } from '../components/ui/Button';
import { Card } from '../components/ui/Card';
import { EmptyState } from '../components/ui/EmptyState';
import { Input } from '../components/ui/Input';
import { SectionHeading } from '../components/ui/SectionHeading';
import { StatusBadge } from '../components/ui/StatusBadge';
import { getErrorMessage } from '../lib/errors';
import { formatDate } from '../lib/format';
import { useAuth } from '../features/auth/useAuth';
import type { Project, RequestStatus, User } from '../types/api';

type Section = 'friends' | 'overview' | 'profile' | 'projects';

const sectionCopy: Record<Section, { description: string; title: string }> = {
  friends: {
    description: 'Busque pessoas, aceite pedidos pendentes e mantenha sua rede ativa.',
    title: 'Amizades',
  },
  overview: {
    description: 'Visão geral do workspace, convites e produtividade recente.',
    title: 'Visão geral',
  },
  profile: {
    description: 'Conta autenticada, upload seguro de foto e informações básicas.',
    title: 'Perfil',
  },
  projects: {
    description: 'Projetos próprios e como membro, com ações rápidas e navegação direta.',
    title: 'Projetos',
  },
};

function initials(name: string) {
  return name
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('');
}

function uniqueProjects(projects: Project[]) {
  return [...new Map(projects.map((project) => [project.id, project])).values()];
}

export function DashboardPage() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { logout, session } = useAuth();
  const [activeSection, setActiveSection] = useState<Section>('overview');
  const [feedback, setFeedback] = useState<string | null>(null);
  const [projectModal, setProjectModal] = useState<{ mode: 'create' | 'edit'; value: Project | null } | null>(null);
  const [searchTerm, setSearchTerm] = useState('');

  const currentUserQuery = useQuery({
    enabled: Boolean(session),
    queryFn: () => getUser(session!.userId),
    queryKey: ['user', session?.userId],
  });

  const ownedProjectsQuery = useQuery({
    queryFn: getOwnedProjects,
    queryKey: ['projects', 'owned'],
  });

  const memberProjectsQuery = useQuery({
    queryFn: getMemberProjects,
    queryKey: ['projects', 'member'],
  });

  const projectInvitesQuery = useQuery({
    queryFn: getPendingProjectInvites,
    queryKey: ['projects', 'invites'],
  });

  const friendsQuery = useQuery({
    queryFn: getFriends,
    queryKey: ['friends'],
  });

  const friendRequestsQuery = useQuery({
    queryFn: getPendingFriendRequests,
    queryKey: ['friendships', 'pending'],
  });

  const searchUsersQuery = useQuery({
    enabled: searchTerm.trim().length >= 2,
    queryFn: () => searchUsers(searchTerm.trim()),
    queryKey: ['users', 'search', searchTerm.trim()],
  });

  const projectMutation = useMutation({
    mutationFn: async (payload: { id?: number; values: { description: string; name: string; type: string } }) => {
      if (payload.id) {
        return updateProject(payload.id, payload.values);
      }

      return createProject(payload.values);
    },
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Projeto salvo com sucesso.');
      setProjectModal(null);
      await queryClient.invalidateQueries({ queryKey: ['projects'] });
    },
  });

  const archiveProjectMutation = useMutation({
    mutationFn: deleteProject,
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Projeto arquivado com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['projects'] });
    },
  });

  const answerProjectInviteMutation = useMutation({
    mutationFn: ({ answer, id }: { answer: Extract<RequestStatus, 'ACCEPTED' | 'REFUSED'>; id: number }) =>
      answerProjectInvite(id, answer),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Convite atualizado.');
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: ['projects', 'invites'] }),
        queryClient.invalidateQueries({ queryKey: ['projects'] }),
      ]);
    },
  });

  const sendFriendRequestMutation = useMutation({
    mutationFn: sendFriendRequest,
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: () => setFeedback('Solicitação enviada com sucesso.'),
  });

  const answerFriendRequestMutation = useMutation({
    mutationFn: ({ answer, id }: { answer: Extract<RequestStatus, 'ACCEPTED' | 'REFUSED'>; id: number }) =>
      answerFriendRequest(id, answer),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Solicitação atualizada.');
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: ['friends'] }),
        queryClient.invalidateQueries({ queryKey: ['friendships', 'pending'] }),
      ]);
    },
  });

  const updatePhotoMutation = useMutation({
    mutationFn: (file: File) => updateProfilePhoto(session!.userId, file),
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: async () => {
      setFeedback('Foto atualizada com sucesso.');
      await queryClient.invalidateQueries({ queryKey: ['user', session?.userId] });
    },
  });

  const ownedProjects = ownedProjectsQuery.data ?? [];
  const memberProjects = memberProjectsQuery.data ?? [];
  const allProjects = uniqueProjects([...ownedProjects, ...memberProjects]);
  const currentUser = currentUserQuery.data;
  const friends = friendsQuery.data ?? [];
  const pendingFriendRequests = friendRequestsQuery.data ?? [];
  const pendingProjectInvites = projectInvitesQuery.data ?? [];

  const currentId = session?.userId;
  const friendIds = new Set(friends.map((friend) => friend.id));
  const searchResults = (searchUsersQuery.data ?? []).filter(
    (user) => user.id !== currentId && !friendIds.has(user.id),
  );

  const isLoading =
    currentUserQuery.isLoading ||
    ownedProjectsQuery.isLoading ||
    memberProjectsQuery.isLoading ||
    projectInvitesQuery.isLoading ||
    friendsQuery.isLoading ||
    friendRequestsQuery.isLoading;

  function handleLogout() {
    logout();
    queryClient.clear();
    navigate('/login');
  }

  async function handleProjectSubmit(values: { description: string; name: string; type: string }) {
    await projectMutation.mutateAsync({
      id: projectModal?.mode === 'edit' ? projectModal.value?.id : undefined,
      values,
    });
  }

  async function handleArchiveProject(projectId: number) {
    const confirmed = window.confirm('Deseja arquivar este projeto?');

    if (!confirmed) {
      return;
    }

    await archiveProjectMutation.mutateAsync(projectId);
  }

  async function handlePhotoInputChange(file?: File) {
    if (!file) {
      return;
    }

    const allowedTypes = new Set(['image/jpeg', 'image/png', 'image/webp']);

    if (!allowedTypes.has(file.type)) {
      setFeedback('Use somente imagens JPG, PNG ou WEBP.');
      return;
    }

    if (file.size > 2 * 1024 * 1024) {
      setFeedback('A imagem deve ter no máximo 2 MB.');
      return;
    }

    await updatePhotoMutation.mutateAsync(file);
  }

  return (
    <main className="mx-auto flex min-h-screen w-full max-w-7xl gap-6 px-4 py-6 lg:px-8">
      <aside className="surface hidden w-72 shrink-0 flex-col justify-between p-6 lg:flex">
        <div className="space-y-8">
          <div className="space-y-3">
            <div className="inline-flex size-14 items-center justify-center rounded-2xl bg-brand-500/15 text-xl font-bold text-brand-100">
              {currentUser ? initials(currentUser.profileName) : 'SM'}
            </div>
            <div>
              <p className="text-lg font-semibold text-white">{currentUser?.profileName ?? session?.username}</p>
              <p className="text-subtle">@{currentUser?.username ?? session?.username}</p>
            </div>
          </div>

          <nav className="space-y-2">
            {(
              [
                { icon: FolderKanban, id: 'overview', label: 'Visão geral' },
                { icon: FolderKanban, id: 'projects', label: 'Projetos' },
                { icon: Users, id: 'friends', label: 'Amizades' },
                { icon: UserCircle2, id: 'profile', label: 'Perfil' },
              ] as const
            ).map((item) => (
              <button
                className={`flex w-full items-center gap-3 rounded-2xl px-4 py-3 text-left text-sm font-medium transition ${
                  activeSection === item.id
                    ? 'bg-brand-500 text-white'
                    : 'text-slate-300 hover:bg-white/5 hover:text-white'
                }`}
                key={item.id}
                onClick={() => setActiveSection(item.id)}
                type="button"
              >
                <item.icon className="size-4" />
                {item.label}
              </button>
            ))}
          </nav>

          <Card className="space-y-3 bg-brand-500/10 p-4">
            <div className="flex items-center gap-3 text-brand-100">
              <Shield className="size-5" />
              <p className="font-semibold">Sessão protegida</p>
            </div>
            <p className="text-sm text-brand-50/80">
              O token fica apenas em sessionStorage para reduzir persistência indevida no navegador.
            </p>
          </Card>
        </div>

        <Button className="w-full justify-start" onClick={handleLogout} variant="secondary">
          <LogOut className="size-4" />
          Sair
        </Button>
      </aside>

      <section className="flex-1 space-y-6">
        <header className="surface flex flex-col gap-4 p-6 md:flex-row md:items-center md:justify-between">
          <div>
            <p className="text-sm uppercase tracking-[0.25em] text-brand-200">Workspace</p>
            <h1 className="mt-2 text-3xl font-semibold text-white">{sectionCopy[activeSection].title}</h1>
            <p className="mt-1 text-subtle">{sectionCopy[activeSection].description}</p>
          </div>
          <div className="flex flex-wrap gap-3">
            <Button className="lg:hidden" onClick={() => setActiveSection('overview')} variant="secondary">
              Visão geral
            </Button>
            <Button onClick={() => setProjectModal({ mode: 'create', value: null })}>
              <Plus className="size-4" />
              Novo projeto
            </Button>
          </div>
        </header>

        {feedback ? (
          <div className="rounded-2xl border border-brand-400/20 bg-brand-500/10 px-4 py-3 text-sm text-brand-50">
            {feedback}
          </div>
        ) : null}

        {isLoading ? (
          <Card className="p-8 text-center text-slate-300">Carregando dados do dashboard...</Card>
        ) : null}

        {!isLoading && activeSection === 'overview' ? (
          <div className="space-y-6">
            <div className="grid gap-4 md:grid-cols-4">
              {[
                { label: 'Projetos próprios', value: ownedProjects.length },
                { label: 'Como membro', value: memberProjects.length },
                { label: 'Convites pendentes', value: pendingProjectInvites.length },
                { label: 'Pedidos de amizade', value: pendingFriendRequests.length },
              ].map((item) => (
                <Card className="space-y-2 p-5" key={item.label}>
                  <p className="text-subtle">{item.label}</p>
                  <p className="text-3xl font-semibold text-white">{item.value}</p>
                </Card>
              ))}
            </div>

            <div className="grid gap-6 xl:grid-cols-[1.2fr_0.8fr]">
              <Card className="space-y-4">
                <SectionHeading
                  description="Acesse rapidamente os projetos mais relevantes."
                  title="Projetos em destaque"
                />
                <div className="grid gap-4 md:grid-cols-2">
                  {allProjects.slice(0, 4).map((project) => (
                    <button
                      className="surface-muted flex flex-col items-start gap-3 p-5 text-left transition hover:border-brand-400/40"
                      key={project.id}
                      onClick={() => navigate(`/projects/${project.id}`)}
                      type="button"
                    >
                      <div className="flex w-full items-center justify-between gap-2">
                        <p className="text-base font-semibold text-white">{project.name}</p>
                        <StatusBadge status={project.creatorId === session?.userId ? 'ACCEPTED' : 'ON_HOLD'} />
                      </div>
                      <p className="text-sm text-slate-400">
                        {project.description?.trim() || 'Projeto sem descrição cadastrada.'}
                      </p>
                      <p className="text-xs uppercase tracking-[0.25em] text-slate-500">
                        Início {formatDate(project.dateStart)}
                      </p>
                    </button>
                  ))}
                  {allProjects.length === 0 ? (
                    <EmptyState
                      description="Crie seu primeiro projeto para montar o backlog e acompanhar sprints."
                      title="Nenhum projeto encontrado"
                    />
                  ) : null}
                </div>
              </Card>

              <Card className="space-y-4">
                <SectionHeading
                  description="Ações pendentes herdadas do fluxo clássico do app."
                  title="Convites e pedidos"
                />
                <div className="space-y-4">
                  {pendingProjectInvites.map((invite) => (
                    <div className="surface-muted space-y-3 p-4" key={invite.id}>
                      <p className="text-sm font-medium text-white">Projeto #{invite.projectId}</p>
                      <p className="text-subtle">Convite enviado em {formatDate(invite.inviteSentDate)}</p>
                      <div className="flex gap-2">
                        <Button
                          isLoading={answerProjectInviteMutation.isPending}
                          onClick={() => answerProjectInviteMutation.mutate({ answer: 'ACCEPTED', id: invite.id })}
                        >
                          Aceitar
                        </Button>
                        <Button
                          isLoading={answerProjectInviteMutation.isPending}
                          onClick={() => answerProjectInviteMutation.mutate({ answer: 'REFUSED', id: invite.id })}
                          variant="secondary"
                        >
                          Recusar
                        </Button>
                      </div>
                    </div>
                  ))}
                  {pendingFriendRequests.map((request) => (
                    <div className="surface-muted space-y-3 p-4" key={request.id}>
                      <p className="text-sm font-medium text-white">{request.requestedByUsername}</p>
                      <p className="text-subtle">Pedido enviado em {formatDate(request.sentDate)}</p>
                      <div className="flex gap-2">
                        <Button
                          isLoading={answerFriendRequestMutation.isPending}
                          onClick={() => answerFriendRequestMutation.mutate({ answer: 'ACCEPTED', id: request.id })}
                        >
                          Aceitar
                        </Button>
                        <Button
                          isLoading={answerFriendRequestMutation.isPending}
                          onClick={() => answerFriendRequestMutation.mutate({ answer: 'REFUSED', id: request.id })}
                          variant="secondary"
                        >
                          Recusar
                        </Button>
                      </div>
                    </div>
                  ))}
                  {pendingProjectInvites.length === 0 && pendingFriendRequests.length === 0 ? (
                    <EmptyState
                      description="Seu fluxo está limpo por agora."
                      title="Nenhuma pendência encontrada"
                    />
                  ) : null}
                </div>
              </Card>
            </div>
          </div>
        ) : null}

        {!isLoading && activeSection === 'projects' ? (
          <div className="grid gap-6 xl:grid-cols-[1.1fr_0.9fr]">
            <Card className="space-y-4">
              <SectionHeading
                description="Projetos em que você lidera ou participa."
                title="Portfólio de projetos"
              />
              <div className="space-y-4">
                {allProjects.map((project) => {
                  const isOwner = project.creatorId === session?.userId;

                  return (
                    <div className="surface-muted space-y-4 p-5" key={project.id}>
                      <div className="flex flex-col gap-3 md:flex-row md:items-start md:justify-between">
                        <div className="space-y-2">
                          <div className="flex flex-wrap items-center gap-2">
                            <p className="text-lg font-semibold text-white">{project.name}</p>
                            <StatusBadge status={isOwner ? 'ACCEPTED' : 'ON_HOLD'} />
                          </div>
                          <p className="text-subtle">{project.description?.trim() || 'Sem descrição.'}</p>
                          <p className="text-xs uppercase tracking-[0.25em] text-slate-500">
                            Tipo {project.type?.trim() || 'não informado'} · início {formatDate(project.dateStart)}
                          </p>
                        </div>
                        <div className="flex flex-wrap gap-2">
                          <Button onClick={() => navigate(`/projects/${project.id}`)} variant="secondary">
                            Abrir
                          </Button>
                          {isOwner ? (
                            <>
                              <Button onClick={() => setProjectModal({ mode: 'edit', value: project })} variant="ghost">
                                Editar
                              </Button>
                              <Button
                                isLoading={archiveProjectMutation.isPending}
                                onClick={() => handleArchiveProject(project.id)}
                                variant="danger"
                              >
                                Arquivar
                              </Button>
                            </>
                          ) : null}
                        </div>
                      </div>
                    </div>
                  );
                })}
                {allProjects.length === 0 ? (
                  <EmptyState
                    action={
                      <Button onClick={() => setProjectModal({ mode: 'create', value: null })}>
                        <Plus className="size-4" />
                        Criar projeto
                      </Button>
                    }
                    description="Monte seu board, convide pessoas e gerencie sprints."
                    title="Você ainda não possui projetos"
                  />
                ) : null}
              </div>
            </Card>

            <Card className="space-y-4">
              <SectionHeading
                description="Convites pendentes de projetos legados ou recém-criados."
                title="Convites para projetos"
              />
              <div className="space-y-4">
                {pendingProjectInvites.map((invite) => (
                  <div className="surface-muted space-y-3 p-4" key={invite.id}>
                    <div className="flex items-center justify-between gap-3">
                      <p className="font-medium text-white">Projeto #{invite.projectId}</p>
                      <StatusBadge status={invite.inviteStatus} />
                    </div>
                    <p className="text-subtle">Enviado em {formatDate(invite.inviteSentDate)}</p>
                    <div className="flex gap-2">
                      <Button
                        isLoading={answerProjectInviteMutation.isPending}
                        onClick={() => answerProjectInviteMutation.mutate({ answer: 'ACCEPTED', id: invite.id })}
                      >
                        Aceitar
                      </Button>
                      <Button
                        isLoading={answerProjectInviteMutation.isPending}
                        onClick={() => answerProjectInviteMutation.mutate({ answer: 'REFUSED', id: invite.id })}
                        variant="secondary"
                      >
                        Recusar
                      </Button>
                    </div>
                  </div>
                ))}
                {pendingProjectInvites.length === 0 ? (
                  <EmptyState
                    description="Quando alguém convidar você, o card aparecerá aqui."
                    title="Sem convites no momento"
                  />
                ) : null}
              </div>
            </Card>
          </div>
        ) : null}

        {!isLoading && activeSection === 'friends' ? (
          <div className="grid gap-6 xl:grid-cols-[1fr_1fr]">
            <Card className="space-y-4">
              <SectionHeading
                description="Busque por nome ou username e convide para colaborar."
                title="Buscar usuários"
              />
              <div className="relative">
                <Search className="pointer-events-none absolute left-4 top-1/2 size-4 -translate-y-1/2 text-slate-500" />
                <Input
                  className="pl-11"
                  onChange={(event) => setSearchTerm(event.target.value)}
                  placeholder="Pesquisar por nome ou usuário"
                  value={searchTerm}
                />
              </div>
              <div className="space-y-3">
                {searchResults.map((user) => (
                  <UserRow
                    action={
                      <Button
                        isLoading={sendFriendRequestMutation.isPending}
                        onClick={() => sendFriendRequestMutation.mutate(user.id)}
                      >
                        <UserPlus className="size-4" />
                        Convidar
                      </Button>
                    }
                    key={user.id}
                    user={user}
                  />
                ))}
                {searchTerm.trim().length >= 2 && !searchUsersQuery.isLoading && searchResults.length === 0 ? (
                  <EmptyState
                    description="Refine a busca ou tente um username diferente."
                    title="Nenhum usuário elegível encontrado"
                  />
                ) : null}
              </div>
            </Card>

            <div className="space-y-6">
              <Card className="space-y-4">
                <SectionHeading
                  description="Pedidos pendentes recebidos na sua conta."
                  title="Solicitações de amizade"
                />
                <div className="space-y-3">
                  {pendingFriendRequests.map((request) => (
                    <div className="surface-muted flex flex-col gap-3 p-4 md:flex-row md:items-center md:justify-between" key={request.id}>
                      <div>
                        <p className="font-medium text-white">{request.requestedByUsername}</p>
                        <p className="text-subtle">Enviado em {formatDate(request.sentDate)}</p>
                      </div>
                      <div className="flex gap-2">
                        <Button
                          isLoading={answerFriendRequestMutation.isPending}
                          onClick={() => answerFriendRequestMutation.mutate({ answer: 'ACCEPTED', id: request.id })}
                        >
                          Aceitar
                        </Button>
                        <Button
                          isLoading={answerFriendRequestMutation.isPending}
                          onClick={() => answerFriendRequestMutation.mutate({ answer: 'REFUSED', id: request.id })}
                          variant="secondary"
                        >
                          Recusar
                        </Button>
                      </div>
                    </div>
                  ))}
                  {pendingFriendRequests.length === 0 ? (
                    <EmptyState
                      description="Nenhum pedido aguardando sua resposta."
                      title="Caixa de entrada zerada"
                    />
                  ) : null}
                </div>
              </Card>

              <Card className="space-y-4">
                <SectionHeading
                  description="Pessoas já conectadas ao seu workspace."
                  title="Sua rede"
                />
                <div className="space-y-3">
                  {friends.map((friend) => (
                    <UserRow key={friend.id} user={friend} />
                  ))}
                  {friends.length === 0 ? (
                    <EmptyState
                      description="Envie convites para começar a montar sua rede."
                      title="Você ainda não possui amizades"
                    />
                  ) : null}
                </div>
              </Card>
            </div>
          </div>
        ) : null}

        {!isLoading && activeSection === 'profile' && currentUser ? (
          <div className="grid gap-6 xl:grid-cols-[0.9fr_1.1fr]">
            <Card className="space-y-6">
              <div className="flex flex-col items-start gap-4">
                <div className="inline-flex size-20 items-center justify-center rounded-3xl bg-brand-500/15 text-3xl font-semibold text-brand-100">
                  {initials(currentUser.profileName)}
                </div>
                <div>
                  <h2 className="text-2xl font-semibold text-white">{currentUser.profileName}</h2>
                  <p className="text-subtle">@{currentUser.username}</p>
                </div>
              </div>

              <div className="space-y-3 text-sm text-slate-300">
                <p>
                  <span className="text-slate-500">Email:</span> {currentUser.email}
                </p>
                <p>
                  <span className="text-slate-500">Status:</span> {currentUser.status}
                </p>
                <p>
                  <span className="text-slate-500">Cadastro:</span> {formatDate(currentUser.registrationDate)}
                </p>
              </div>
            </Card>

            <Card className="space-y-4">
              <SectionHeading
                description="Envie somente imagens seguras e leves para a sua conta."
                title="Atualizar foto"
              />
              <div className="surface-muted space-y-4 p-5">
                <p className="text-subtle">
                  O backend atualiza a foto em binário. Por segurança, a UI aceita apenas JPG, PNG e WEBP até 2 MB.
                </p>
                <input
                  accept="image/png,image/jpeg,image/webp"
                  className="block w-full text-sm text-slate-300 file:mr-4 file:rounded-2xl file:border-0 file:bg-brand-500 file:px-4 file:py-3 file:font-semibold file:text-white"
                  onChange={(event) => handlePhotoInputChange(event.target.files?.[0])}
                  type="file"
                />
                <Button isLoading={updatePhotoMutation.isPending} onClick={() => setFeedback('Selecione uma imagem para enviar.')} variant="ghost">
                  Selecionar arquivo acima
                </Button>
              </div>
            </Card>
          </div>
        ) : null}

        {projectModal ? (
          <ProjectFormModal
            initialValue={projectModal.value}
            isSaving={projectMutation.isPending}
            onClose={() => setProjectModal(null)}
            onSubmit={handleProjectSubmit}
          />
        ) : null}
      </section>
    </main>
  );
}

function UserRow({ action, user }: { action?: ReactNode; user: User }) {
  return (
    <div className="surface-muted flex flex-col gap-3 p-4 md:flex-row md:items-center md:justify-between">
      <div>
        <p className="font-medium text-white">{user.profileName}</p>
        <p className="text-subtle">
          @{user.username} · {user.email}
        </p>
      </div>
      {action}
    </div>
  );
}
