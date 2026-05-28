import { useEffect, useState } from 'react';
import type { Project, ProjectPayload } from '../../types/api';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { Modal } from '../ui/Modal';
import { Textarea } from '../ui/Textarea';

type Props = {
  initialValue?: Project | null;
  isSaving?: boolean;
  onClose: () => void;
  onSubmit: (payload: ProjectPayload) => void | Promise<void>;
};

const emptyState: ProjectPayload = {
  description: '',
  name: '',
  type: '',
};

export function ProjectFormModal({ initialValue, isSaving = false, onClose, onSubmit }: Props) {
  const [form, setForm] = useState<ProjectPayload>(emptyState);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!initialValue) {
      setForm(emptyState);
      return;
    }

    setForm({
      description: initialValue.description ?? '',
      name: initialValue.name,
      type: initialValue.type ?? '',
    });
  }, [initialValue]);

  async function handleSubmit() {
    if (form.name.trim().length < 3) {
      setError('O nome do projeto precisa ter ao menos 3 caracteres.');
      return;
    }

    setError(null);
    await onSubmit({
      description: form.description.trim(),
      name: form.name.trim(),
      type: form.type.trim(),
    });
  }

  return (
    <Modal
      description="Mantenha descrições objetivas, tipos consistentes e dados mínimos."
      onClose={onClose}
      title={initialValue ? 'Editar projeto' : 'Novo projeto'}
    >
      <div className="space-y-4">
        <Field error={undefined} label="Nome">
          <Input
            maxLength={100}
            onChange={(event) => setForm((current) => ({ ...current, name: event.target.value }))}
            value={form.name}
          />
        </Field>
        <Field hint="Ex.: Produto, Time interno, Cliente." label="Tipo">
          <Input
            maxLength={80}
            onChange={(event) => setForm((current) => ({ ...current, type: event.target.value }))}
            value={form.type}
          />
        </Field>
        <Field hint="Opcional." label="Descrição">
          <Textarea
            maxLength={500}
            onChange={(event) => setForm((current) => ({ ...current, description: event.target.value }))}
            value={form.description}
          />
        </Field>
        {error ? <p className="text-sm text-rose-300">{error}</p> : null}
        <div className="flex justify-end gap-3">
          <Button onClick={onClose} variant="ghost">
            Cancelar
          </Button>
          <Button isLoading={isSaving} onClick={handleSubmit}>
            Salvar projeto
          </Button>
        </div>
      </div>
    </Modal>
  );
}
