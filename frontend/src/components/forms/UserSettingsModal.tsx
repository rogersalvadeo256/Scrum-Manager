import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Copy, Key, Lock, Plus, Trash2, User } from 'lucide-react';
import { useState } from 'react';
import {
  changePassword,
  createApiKey,
  deleteApiKey,
  listApiKeys,
  updateProfile,
} from '../../api/users';
import { getErrorMessage } from '../../lib/errors';
import { formatDate } from '../../lib/format';
import type { ApiKey, Availability, ChangePasswordPayload, CreateApiKeyPayload, UpdateProfilePayload, UserSettings } from '../../types/api';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { Modal } from '../ui/Modal';
import { Select } from '../ui/Select';

type Tab = 'apikeys' | 'password' | 'profile';

type Props = {
  onClose: () => void;
  settings: UserSettings;
};

export function UserSettingsModal({ onClose, settings }: Props) {
  const [activeTab, setActiveTab] = useState<Tab>('profile');
  const [feedback, setFeedback] = useState<{ message: string; type: 'error' | 'success' } | null>(null);
  const queryClient = useQueryClient();

  function showFeedback(message: string, type: 'error' | 'success' = 'success') {
    setFeedback({ message, type });
  }

  const tabs: { icon: React.ElementType; id: Tab; label: string }[] = [
    { icon: User, id: 'profile', label: 'Perfil' },
    { icon: Lock, id: 'password', label: 'Senha' },
    { icon: Key, id: 'apikeys', label: 'API Keys' },
  ];

  return (
    <Modal description="Gerencie seu perfil, senha e chaves de API." onClose={onClose} title="Configurações">
      <div className="flex gap-2 border-b border-white/10 pb-4">
        {tabs.map((tab) => (
          <button
            className={`flex items-center gap-2 rounded-xl px-4 py-2 text-sm font-medium transition ${
              activeTab === tab.id
                ? 'bg-brand-500 text-white'
                : 'text-slate-400 hover:bg-white/5 hover:text-white'
            }`}
            key={tab.id}
            onClick={() => { setActiveTab(tab.id); setFeedback(null); }}
            type="button"
          >
            <tab.icon className="size-4" />
            {tab.label}
          </button>
        ))}
      </div>

      {feedback ? (
        <div
          className={`rounded-xl px-4 py-3 text-sm ${
            feedback.type === 'success'
              ? 'bg-brand-500/10 text-brand-100'
              : 'bg-rose-500/10 text-rose-200'
          }`}
        >
          {feedback.message}
        </div>
      ) : null}

      {activeTab === 'profile' ? (
        <ProfileTab
          settings={settings}
          onSuccess={async () => {
            showFeedback('Perfil atualizado com sucesso.');
            await queryClient.invalidateQueries({ queryKey: ['user-settings'] });
            await queryClient.invalidateQueries({ queryKey: ['user', settings.id] });
          }}
          onError={(err) => showFeedback(getErrorMessage(err), 'error')}
        />
      ) : null}

      {activeTab === 'password' ? (
        <PasswordTab
          onSuccess={() => showFeedback('Senha alterada com sucesso. Faça login novamente se necessário.')}
          onError={(err) => showFeedback(getErrorMessage(err), 'error')}
        />
      ) : null}

      {activeTab === 'apikeys' ? (
        <ApiKeysTab
          onError={(err) => showFeedback(getErrorMessage(err), 'error')}
          onSuccess={(msg) => showFeedback(msg)}
        />
      ) : null}
    </Modal>
  );
}

// ─── Profile Tab ──────────────────────────────────────────────────────────────

function ProfileTab({
  settings,
  onSuccess,
  onError,
}: {
  settings: UserSettings;
  onSuccess: (updated: UserSettings) => void;
  onError: (err: unknown) => void;
}) {
  const [form, setForm] = useState<UpdateProfilePayload>({
    availability: settings.availability,
    name: settings.profileName,
  });

  const mutation = useMutation({
    mutationFn: (payload: UpdateProfilePayload) => updateProfile(payload),
    onError,
    onSuccess,
  });

  return (
    <div className="space-y-4">
      <Field label="Nome de exibição">
        <Input
          maxLength={100}
          onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
          value={form.name}
        />
      </Field>
      <Field label="Disponibilidade">
        <Select
          onChange={(e) => setForm((f) => ({ ...f, availability: e.target.value as Availability }))}
          value={form.availability}
        >
          <option value="AVAILABLE">Disponível</option>
          <option value="BUSY">Ocupado</option>
        </Select>
      </Field>
      <div className="space-y-1 rounded-xl bg-white/5 p-4 text-sm text-slate-400">
        <p><span className="text-slate-500">Usuário:</span> @{settings.username}</p>
        <p><span className="text-slate-500">E-mail:</span> {settings.email}</p>
        <p><span className="text-slate-500">Membro desde:</span> {formatDate(settings.registrationDate)}</p>
      </div>
      <div className="flex justify-end">
        <Button isLoading={mutation.isPending} onClick={() => mutation.mutate(form)}>
          Salvar perfil
        </Button>
      </div>
    </div>
  );
}

// ─── Password Tab ─────────────────────────────────────────────────────────────

function PasswordTab({ onSuccess, onError }: { onSuccess: () => void; onError: (err: unknown) => void }) {
  const [form, setForm] = useState<ChangePasswordPayload>({ currentPassword: '', newPassword: '' });
  const [confirm, setConfirm] = useState('');
  const [validationError, setValidationError] = useState<string | null>(null);

  const mutation = useMutation({
    mutationFn: (payload: ChangePasswordPayload) => changePassword(payload),
    onError,
    onSuccess: () => {
      setForm({ currentPassword: '', newPassword: '' });
      setConfirm('');
      onSuccess();
    },
  });

  function handleSubmit() {
    if (form.newPassword !== confirm) {
      setValidationError('As senhas não coincidem.');
      return;
    }
    if (form.newPassword.length < 12) {
      setValidationError('A nova senha deve ter ao menos 12 caracteres.');
      return;
    }
    setValidationError(null);
    mutation.mutate(form);
  }

  return (
    <div className="space-y-4">
      <Field label="Senha atual">
        <Input
          onChange={(e) => setForm((f) => ({ ...f, currentPassword: e.target.value }))}
          type="password"
          value={form.currentPassword}
        />
      </Field>
      <Field label="Nova senha">
        <Input
          onChange={(e) => setForm((f) => ({ ...f, newPassword: e.target.value }))}
          type="password"
          value={form.newPassword}
        />
      </Field>
      <Field label="Confirmar nova senha">
        <Input
          onChange={(e) => setConfirm(e.target.value)}
          type="password"
          value={confirm}
        />
      </Field>
      {validationError ? <p className="text-sm text-rose-300">{validationError}</p> : null}
      <div className="flex justify-end">
        <Button isLoading={mutation.isPending} onClick={handleSubmit}>
          Alterar senha
        </Button>
      </div>
    </div>
  );
}

// ─── API Keys Tab ─────────────────────────────────────────────────────────────

function ApiKeysTab({ onSuccess, onError }: { onSuccess: (msg: string) => void; onError: (err: unknown) => void }) {
  const queryClient = useQueryClient();
  const [newKey, setNewKey] = useState<ApiKey | null>(null);
  const [form, setForm] = useState<CreateApiKeyPayload>({ expiresInDays: null, name: '' });

  const keysQuery = useQuery({
    queryFn: listApiKeys,
    queryKey: ['api-keys'],
  });

  const createMutation = useMutation({
    mutationFn: (payload: CreateApiKeyPayload) => createApiKey(payload),
    onError,
    onSuccess: async (created) => {
      setNewKey(created);
      setForm({ expiresInDays: null, name: '' });
      await queryClient.invalidateQueries({ queryKey: ['api-keys'] });
      onSuccess('Chave criada! Copie o valor agora — ele não será exibido novamente.');
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (keyId: number) => deleteApiKey(keyId),
    onError,
    onSuccess: async () => {
      setNewKey(null);
      await queryClient.invalidateQueries({ queryKey: ['api-keys'] });
      onSuccess('Chave revogada com sucesso.');
    },
  });

  const keys = keysQuery.data ?? [];

  return (
    <div className="space-y-5">
      {newKey?.rawKey ? (
        <div className="rounded-xl border border-brand-400/30 bg-brand-500/10 p-4 space-y-2">
          <p className="text-sm font-semibold text-brand-100">Salve esta chave agora — não será exibida novamente.</p>
          <div className="flex items-center gap-3">
            <code className="flex-1 break-all rounded-lg bg-slate-900 px-3 py-2 text-xs text-brand-200">{newKey.rawKey}</code>
            <Button
              className="shrink-0"
              onClick={() => { navigator.clipboard.writeText(newKey.rawKey!); }}
              variant="ghost"
            >
              <Copy className="size-4" />
            </Button>
          </div>
        </div>
      ) : null}

      <div className="space-y-3">
        <p className="text-sm font-medium text-white">Criar nova chave</p>
        <div className="flex gap-3">
          <Input
            className="flex-1"
            maxLength={100}
            onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
            placeholder="Nome da chave (ex.: CI, Integração externa)"
            value={form.name}
          />
          <Input
            className="w-32"
            min={1}
            onChange={(e) => setForm((f) => ({ ...f, expiresInDays: e.target.value ? Number(e.target.value) : null }))}
            placeholder="Dias"
            title="Expira em X dias (deixe vazio = sem expiração)"
            type="number"
            value={form.expiresInDays ?? ''}
          />
          <Button
            isLoading={createMutation.isPending}
            onClick={() => form.name.trim() && createMutation.mutate(form)}
          >
            <Plus className="size-4" />
            Criar
          </Button>
        </div>
      </div>

      <div className="space-y-2">
        {keysQuery.isLoading ? <p className="text-sm text-slate-400">Carregando…</p> : null}
        {keys.map((key) => (
          <div className="surface-muted flex items-center justify-between gap-4 p-4" key={key.id}>
            <div>
              <p className="font-medium text-white">{key.name}</p>
              <p className="text-xs text-slate-500">
                Prefixo: <code className="text-slate-300">{key.keyPrefix}…</code> · Criada em {formatDate(key.createdAt)}
                {key.expiresAt ? ` · Expira em ${formatDate(key.expiresAt)}` : ''}
              </p>
            </div>
            <Button
              isLoading={deleteMutation.isPending}
              onClick={() => deleteMutation.mutate(key.id)}
              variant="danger"
            >
              <Trash2 className="size-4" />
            </Button>
          </div>
        ))}
        {!keysQuery.isLoading && keys.length === 0 ? (
          <p className="text-sm text-slate-400">Nenhuma chave ativa no momento.</p>
        ) : null}
      </div>
    </div>
  );
}
