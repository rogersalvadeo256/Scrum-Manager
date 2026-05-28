import { z } from 'zod';

export const loginSchema = z.object({
  password: z.string().min(8, 'A senha precisa ter ao menos 8 caracteres.'),
  usernameOrEmail: z
    .string()
    .min(3, 'Informe seu usuário ou email.')
    .max(100, 'Valor muito longo.'),
});

export const registerSchema = z
  .object({
    confirmPassword: z.string().min(8, 'Confirme sua senha.'),
    email: z.string().email('Informe um email válido.'),
    password: z.string().min(8, 'A senha precisa ter ao menos 8 caracteres.'),
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
  newPassword: z.string().min(8, 'A nova senha precisa ter ao menos 8 caracteres.'),
  securityAnswer: z.string().min(2, 'Informe a resposta de segurança.').max(100),
  securityQuestion: z.string().min(5, 'Informe a pergunta de segurança.').max(120),
  usernameOrEmail: z.string().min(3, 'Informe seu usuário ou email.').max(100),
});

export type LoginFormValues = z.infer<typeof loginSchema>;
export type RegisterFormValues = z.infer<typeof registerSchema>;
export type ActivateFormValues = z.infer<typeof activateSchema>;
