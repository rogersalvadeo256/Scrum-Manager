import { useMutation } from '@tanstack/react-query';
import { CheckCircle2, LockKeyhole, ShieldCheck, Sparkles } from 'lucide-react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { activateAccount, login, register } from '../api/auth';
import { ActivateAccountForm } from '../components/auth/ActivateAccountForm';
import type {
  ActivateFormValues,
  LoginFormValues,
  RegisterFormValues,
} from '../components/auth/authSchemas';
import { LoginForm } from '../components/auth/LoginForm';
import { RegisterForm } from '../components/auth/RegisterForm';
import { Card } from '../components/ui/Card';
import { getErrorMessage } from '../lib/errors';
import { useAuth } from '../features/auth/AuthContext';

type TabId = 'activate' | 'login' | 'register';

const tabs: { id: TabId; label: string }[] = [
  { id: 'login', label: 'Entrar' },
  { id: 'register', label: 'Cadastrar' },
  { id: 'activate', label: 'Reativar conta' },
];

export function AuthPage() {
  const navigate = useNavigate();
  const { saveSession } = useAuth();
  const [activeTab, setActiveTab] = useState<TabId>('login');
  const [feedback, setFeedback] = useState<string | null>(null);

  const loginMutation = useMutation({
    mutationFn: login,
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: (session) => {
      saveSession(session);
      setFeedback(null);
      navigate('/');
    },
  });

  const registerMutation = useMutation({
    mutationFn: register,
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: () => {
      setActiveTab('login');
      setFeedback('Conta criada com sucesso. Faça login para continuar.');
    },
  });

  const activateMutation = useMutation({
    mutationFn: activateAccount,
    onError: (error) => setFeedback(getErrorMessage(error)),
    onSuccess: () => {
      setActiveTab('login');
      setFeedback('Conta reativada. Entre com sua nova senha.');
    },
  });

  async function handleLogin(values: LoginFormValues) {
    setFeedback(null);
    await loginMutation.mutateAsync(values);
  }

  async function handleRegister(values: RegisterFormValues) {
    setFeedback(null);
    await registerMutation.mutateAsync({
      email: values.email.trim(),
      password: values.password,
      profileName: values.profileName.trim(),
      securityAnswer: values.securityAnswer.trim(),
      securityQuestion: values.securityQuestion.trim(),
      username: values.username.trim(),
    });
  }

  async function handleActivate(values: ActivateFormValues) {
    setFeedback(null);
    await activateMutation.mutateAsync({
      newPassword: values.newPassword,
      securityAnswer: values.securityAnswer.trim(),
      securityQuestion: values.securityQuestion.trim(),
      usernameOrEmail: values.usernameOrEmail.trim(),
    });
  }

  return (
    <main className="mx-auto grid min-h-screen w-full max-w-7xl gap-8 px-4 py-8 lg:grid-cols-[1.1fr_0.9fr] lg:px-8">
      <section className="surface relative overflow-hidden p-8 lg:p-12">
        <div className="absolute inset-x-0 top-0 h-48 bg-gradient-to-br from-brand-500/20 via-brand-300/5 to-transparent" />
        <div className="relative flex h-full flex-col justify-between gap-10">
          <div className="space-y-6">
            <span className="inline-flex items-center gap-2 rounded-full border border-brand-400/30 bg-brand-500/10 px-4 py-2 text-xs font-semibold uppercase tracking-[0.25em] text-brand-100">
              <Sparkles className="size-4" />
              Scrum Manager Web
            </span>
            <div className="space-y-4">
              <h1 className="max-w-xl text-4xl font-semibold leading-tight text-white md:text-5xl">
                Um frontend React moderno, seguro e preparado para o fluxo do legado.
              </h1>
              <p className="max-w-2xl text-base leading-7 text-slate-300">
                O novo painel concentra autenticação, projetos, sprints, tarefas, amizades e perfil
                com sessões protegidas, validação forte e UI escalável com Tailwind e Lucide.
              </p>
            </div>
          </div>

          <div className="grid gap-4 md:grid-cols-3">
            {[
              {
                description: 'Token em sessionStorage, rotas protegidas e API centralizada.',
                icon: ShieldCheck,
                title: 'Segurança primeiro',
              },
              {
                description: 'Estrutura modular, formulários enxutos e componentes reutilizáveis.',
                icon: CheckCircle2,
                title: 'Clean code',
              },
              {
                description: 'Fluxo de login, cadastro e reativação alinhado ao app original.',
                icon: LockKeyhole,
                title: 'Transição suave',
              },
            ].map((item) => (
              <Card className="space-y-4 bg-white/5 p-5" key={item.title}>
                <item.icon className="size-8 text-brand-300" />
                <div className="space-y-2">
                  <h2 className="text-lg font-semibold text-white">{item.title}</h2>
                  <p className="text-sm leading-6 text-slate-400">{item.description}</p>
                </div>
              </Card>
            ))}
          </div>
        </div>
      </section>

      <section className="flex items-center">
        <Card className="w-full p-6 sm:p-8">
          <div className="mb-8 space-y-3">
            <p className="text-sm font-medium uppercase tracking-[0.3em] text-brand-200">Acesso</p>
            <h2 className="text-3xl font-semibold text-white">Entre no seu workspace</h2>
            <p className="text-subtle">Use JWT, formulários validados e estados previsíveis.</p>
          </div>

          <div className="mb-6 flex flex-wrap gap-2">
            {tabs.map((tab) => (
              <button
                className={`rounded-full px-4 py-2 text-sm font-semibold transition ${
                  activeTab === tab.id
                    ? 'bg-brand-500 text-white'
                    : 'bg-white/5 text-slate-300 hover:bg-white/10'
                }`}
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                type="button"
              >
                {tab.label}
              </button>
            ))}
          </div>

          {feedback ? (
            <div className="mb-6 rounded-2xl border border-brand-400/20 bg-brand-500/10 px-4 py-3 text-sm text-brand-50">
              {feedback}
            </div>
          ) : null}

          {activeTab === 'login' ? <LoginForm isLoading={loginMutation.isPending} onSubmit={handleLogin} /> : null}
          {activeTab === 'register' ? (
            <RegisterForm isLoading={registerMutation.isPending} onSubmit={handleRegister} />
          ) : null}
          {activeTab === 'activate' ? (
            <ActivateAccountForm isLoading={activateMutation.isPending} onSubmit={handleActivate} />
          ) : null}
        </Card>
      </section>
    </main>
  );
}
