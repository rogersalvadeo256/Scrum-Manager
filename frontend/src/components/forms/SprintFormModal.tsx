import { useState } from 'react';
import type { Sprint, SprintPayload, SprintStatus } from '../../types/api';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { Modal } from '../ui/Modal';
import { Select } from '../ui/Select';
import { Textarea } from '../ui/Textarea';

type Props = {
  initialValue?: Sprint | null;
  isSaving?: boolean;
  onClose: () => void;
  onSubmit: (payload: SprintPayload) => void | Promise<void>;
};

const emptyState: SprintPayload = {
  points: 0,
  status: 'DOING',
  text: '',
  title: '',
};

const statusOptions: { label: string; value: SprintStatus }[] = [
  { label: 'Em andamento', value: 'DOING' },
  { label: 'Concluído', value: 'DONE' },
];

export function SprintFormModal({ initialValue, isSaving = false, onClose, onSubmit }: Props) {
  const [form, setForm] = useState<SprintPayload>(() =>
    initialValue
      ? {
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
      setError('O sprint precisa ter um título com ao menos 3 caracteres.');
      return;
    }

    setError(null);
    await onSubmit({
      points: Number.isNaN(form.points) ? 0 : form.points,
      status: form.status,
      text: form.text.trim(),
      title: form.title.trim(),
    });
  }

  return (
    <Modal onClose={onClose} title={initialValue ? 'Editar sprint' : 'Novo sprint'}>
      <div className="space-y-4">
        <Field label="Título">
          <Input
            maxLength={100}
            onChange={(event) => setForm((current) => ({ ...current, title: event.target.value }))}
            value={form.title}
          />
        </Field>
        <div className="grid gap-4 md:grid-cols-2">
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
          <Field label="Status">
            <Select
              onChange={(event) =>
                setForm((current) => ({ ...current, status: event.target.value as SprintStatus }))
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
            Salvar sprint
          </Button>
        </div>
      </div>
    </Modal>
  );
}
