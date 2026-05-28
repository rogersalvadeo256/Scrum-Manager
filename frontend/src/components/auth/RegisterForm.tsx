import { zodResolver } from '@hookform/resolvers/zod';
import { UserPlus } from 'lucide-react';
import { useForm } from 'react-hook-form';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { registerSchema, type RegisterFormValues } from './authSchemas';

type Props = {
  isLoading?: boolean;
  onSubmit: (values: RegisterFormValues) => void | Promise<void>;
};

export function RegisterForm({ isLoading = false, onSubmit }: Props) {
  const {
    formState: { errors },
    handleSubmit,
    register,
  } = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
  });

  return (
    <form className="space-y-4" onSubmit={handleSubmit(async (values) => onSubmit(values))}>
      <div className="grid gap-4 md:grid-cols-2">
        <Field error={errors.profileName?.message} label="Nome de exibição">
          <Input placeholder="Jefter Gomes" {...register('profileName')} />
        </Field>
        <Field error={errors.username?.message} label="Usuário">
          <Input autoComplete="username" placeholder="jefter66" {...register('username')} />
        </Field>
      </div>
      <Field error={errors.email?.message} label="Email">
        <Input autoComplete="email" placeholder="voce@empresa.com" type="email" {...register('email')} />
      </Field>
      <div className="grid gap-4 md:grid-cols-2">
        <Field error={errors.securityQuestion?.message} label="Pergunta de segurança">
          <Input placeholder="Qual é o nome do seu primeiro projeto?" {...register('securityQuestion')} />
        </Field>
        <Field error={errors.securityAnswer?.message} label="Resposta de segurança">
          <Input placeholder="Scrum Legacy" {...register('securityAnswer')} />
        </Field>
      </div>
      <div className="grid gap-4 md:grid-cols-2">
        <Field error={errors.password?.message} label="Senha">
          <Input autoComplete="new-password" type="password" {...register('password')} />
        </Field>
        <Field error={errors.confirmPassword?.message} label="Confirmar senha">
          <Input autoComplete="new-password" type="password" {...register('confirmPassword')} />
        </Field>
      </div>
      <Button className="w-full" isLoading={isLoading} type="submit">
        <UserPlus className="size-4" />
        Criar conta
      </Button>
    </form>
  );
}
