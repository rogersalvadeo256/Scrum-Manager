import { z } from 'zod';

const passwordSchema = z
  .string()
  .min(12, 'A senha precisa ter ao menos 12 caracteres.')
  .refine((value) => /[A-Z]/.test(value), 'A senha precisa conter ao menos uma letra maiúscula.')
  .refine((value) => /[a-z]/.test(value), 'A senha precisa conter ao menos uma letra minúscula.')
  .refine((value) => /\d/.test(value), 'A senha precisa conter ao menos um número.')
  .refine((value) => /[^A-Za-z0-9]/.test(value), 'A senha precisa conter ao menos um caractere especial.');

export const loginSchema = z.object({
  password: z.string().min(1, 'Informe sua senha.'),
  usernameOrEmail: z
    .string()
    .min(3, 'Informe seu usuário ou email.')
    .max(100, 'Valor muito longo.'),
});

export const registerSchema = z
  .object({
    confirmPassword: z.string().min(12, 'Confirme sua senha.'),
    email: z.string().email('Informe um email válido.'),
    password: passwordSchema,
    profileName: z.string().min(3, 'Informe seu nome de exibição.').max(100),
    securityAnswer: z.string().min(2, 'Informe a resposta de segurança.').max(100),
    securityQuestion: z.string().min(5, 'Informe uma pergunta de segurança.').max(120),
    username: z.string().min(3, 'Informe um nome de usuário.').max(50),
  })
  .refine((value) => value.password === value.confirmPassword, {
    message: 'As senhas precisam ser iguais.',
    path: ['confirmPassword'],
  });

export const activateSchema = z.object({
  newPassword: passwordSchema,
  securityAnswer: z.string().min(2, 'Informe a resposta de segurança.').max(100),
  securityQuestion: z.string().min(5, 'Informe a pergunta de segurança.').max(120),
  usernameOrEmail: z.string().min(3, 'Informe seu usuário ou email.').max(100),
});

export type LoginFormValues = z.infer<typeof loginSchema>;
export type RegisterFormValues = z.infer<typeof registerSchema>;
export type ActivateFormValues = z.infer<typeof activateSchema>;
