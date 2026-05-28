import { useState } from 'react';
import type { Task, TaskPayload, TaskStatus, User } from '../../types/api';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { Modal } from '../ui/Modal';
import { Select } from '../ui/Select';
import { Textarea } from '../ui/Textarea';

type Props = {
  initialValue?: Task | null;
  isSaving?: boolean;
  members: User[];
  onClose: () => void;
  onSubmit: (payload: TaskPayload) => void | Promise<void>;
};

const emptyState: TaskPayload = {
  executorId: null,
  points: 0,
  status: 'TO_DO',
  text: '',
  title: '',
};

const statusOptions: { label: string; value: TaskStatus }[] = [
  { label: 'A fazer', value: 'TO_DO' },
  { label: 'Em andamento', value: 'DOING' },
  { label: 'Concluído', value: 'DONE' },
];

export function TaskFormModal({
  initialValue,
  isSaving = false,
  members,
  onClose,
  onSubmit,
}: Props) {
  const [form, setForm] = useState<TaskPayload>(() =>
    initialValue
      ? {
          executorId: initialValue.executorId,
          points: initialValue.points,
          status: initialValue.status,
          text: initialValue.text ?? '',
          title: initialValue.title,
        }
      : emptyState,
  );
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit() {
    if (form.title.trim().length < 3) {
      setError('A tarefa precisa ter um título com ao menos 3 caracteres.');
      return;
    }

    setError(null);
    await onSubmit({
      executorId: form.executorId,
      points: Number.isNaN(form.points) ? 0 : form.points,
      status: form.status,
      text: form.text.trim(),
      title: form.title.trim(),
    });
  }

  return (
    <Modal
      description="Somente o criador da tarefa poderá editar ou remover depois."
      onClose={onClose}
      title={initialValue ? 'Editar tarefa' : 'Nova tarefa'}
    >
      <div className="space-y-4">
        <Field label="Título">
          <Input
            maxLength={100}
            onChange={(event) => setForm((current) => ({ ...current, title: event.target.value }))}
            value={form.title}
          />
        </Field>
        <div className="grid gap-4 md:grid-cols-3">
          <Field label="Pontos">
            <Input
              min={0}
              onChange={(event) =>
                setForm((current) => ({ ...current, points: Number.parseInt(event.target.value || '0', 10) }))
              }
              type="number"
              value={form.points}
            />
          </Field>
          <Field label="Executor">
            <Select
              onChange={(event) =>
                setForm((current) => ({
                  ...current,
                  executorId: event.target.value ? Number.parseInt(event.target.value, 10) : null,
                }))
              }
              value={form.executorId ?? ''}
            >
              <option value="">Sem responsável</option>
              {members.map((member) => (
                <option key={member.id} value={member.id}>
                  {member.profileName} ({member.username})
                </option>
              ))}
            </Select>
          </Field>
          <Field label="Status">
            <Select
              onChange={(event) =>
                setForm((current) => ({ ...current, status: event.target.value as TaskStatus }))
              }
              value={form.status}
            >
              {statusOptions.map((option) => (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              ))}
            </Select>
          </Field>
        </div>
        <Field hint="Opcional." label="Descrição">
          <Textarea
            maxLength={500}
            onChange={(event) => setForm((current) => ({ ...current, text: event.target.value }))}
            value={form.text}
          />
        </Field>
        {error ? <p className="text-sm text-rose-300">{error}</p> : null}
        <div className="flex justify-end gap-3">
          <Button onClick={onClose} variant="ghost">
            Cancelar
          </Button>
          <Button isLoading={isSaving} onClick={handleSubmit}>
            Salvar tarefa
          </Button>
        </div>
      </div>
    </Modal>
  );
}
