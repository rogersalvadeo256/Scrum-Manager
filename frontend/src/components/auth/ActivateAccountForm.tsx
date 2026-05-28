import { zodResolver } from '@hookform/resolvers/zod';
import { KeyRound } from 'lucide-react';
import { useForm } from 'react-hook-form';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { activateSchema, type ActivateFormValues } from './authSchemas';

type Props = {
  isLoading?: boolean;
  onSubmit: (values: ActivateFormValues) => void | Promise<void>;
};

export function ActivateAccountForm({ isLoading = false, onSubmit }: Props) {
  const {
    formState: { errors },
    handleSubmit,
    register,
  } = useForm<ActivateFormValues>({
    resolver: zodResolver(activateSchema),
  });

  return (
    <form className="space-y-4" onSubmit={handleSubmit(async (values) => onSubmit(values))}>
      <Field error={errors.usernameOrEmail?.message} label="Usuário ou email">
        <Input placeholder="jefter66" {...register('usernameOrEmail')} />
      </Field>
      <Field error={errors.securityQuestion?.message} label="Pergunta de segurança">
        <Input placeholder="Qual era o nome do time?" {...register('securityQuestion')} />
      </Field>
      <Field error={errors.securityAnswer?.message} label="Resposta">
        <Input placeholder="Resposta" {...register('securityAnswer')} />
      </Field>
      <Field error={errors.newPassword?.message} label="Nova senha">
        <Input type="password" {...register('newPassword')} />
      </Field>
      <Button className="w-full" isLoading={isLoading} type="submit" variant="secondary">
        <KeyRound className="size-4" />
        Reativar conta
      </Button>
    </form>
  );
}
