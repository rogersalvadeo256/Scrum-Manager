import { zodResolver } from '@hookform/resolvers/zod';
import { LogIn } from 'lucide-react';
import { useForm } from 'react-hook-form';
import { Button } from '../ui/Button';
import { Field } from '../ui/Field';
import { Input } from '../ui/Input';
import { loginSchema, type LoginFormValues } from './authSchemas';

type Props = {
  isLoading?: boolean;
  onSubmit: (values: LoginFormValues) => void | Promise<void>;
};

export function LoginForm({ isLoading = false, onSubmit }: Props) {
  const {
    formState: { errors },
    handleSubmit,
    register,
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  return (
    <form className="space-y-4" onSubmit={handleSubmit(async (values) => onSubmit(values))}>
      <Field error={errors.usernameOrEmail?.message} label="Usuário ou email">
        <Input autoComplete="username" placeholder="jefter66" {...register('usernameOrEmail')} />
      </Field>
      <Field error={errors.password?.message} label="Senha">
        <Input autoComplete="current-password" placeholder="********" type="password" {...register('password')} />
      </Field>
      <Button className="w-full" isLoading={isLoading} type="submit">
        <LogIn className="size-4" />
        Entrar
      </Button>
    </form>
  );
}
