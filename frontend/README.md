# Scrum Manager Frontend

Frontend React moderno para o Scrum Manager, alinhado ao fluxo do app legado e integrado ao backend Spring Boot com JWT.

## Stack

- React 19 + TypeScript + Vite
- Tailwind CSS
- Lucide React
- TanStack Query
- React Hook Form + Zod
- Axios

## Fluxos implementados

- login
- cadastro
- reativação de conta
- dashboard com visão geral
- projetos próprios e como membro
- convites de projeto
- busca de usuários e amizades
- sprints e tarefas por projeto
- perfil com upload seguro de foto

## Segurança

- token salvo em `sessionStorage`
- rotas protegidas no cliente
- validação de formulários com Zod
- logout integrado ao backend para invalidar o token atual
- políticas de senha alinhadas ao backend (12+ caracteres e complexidade obrigatória)
- upload de foto restrito a `jpg`, `png` e `webp` com limite de 2 MB
- API base configurável por variável de ambiente

## Executar

```bash
cd frontend
cp .env.example .env
pnpm install
pnpm run dev
```

Por padrão o frontend espera a API em `http://localhost:8080`.

## Build

```bash
cd frontend
pnpm run build
```

## Variáveis de ambiente

| Variável | Descrição |
| --- | --- |
| `VITE_API_URL` | URL base da API do backend |
