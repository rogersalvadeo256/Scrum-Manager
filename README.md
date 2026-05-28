# Scrum Manager

Plataforma full stack para gestão de projetos Scrum com backend Spring Boot, frontend React e foco em segurança, automação e integrações assíncronas.

## Visão geral

O repositório está dividido em dois aplicativos:

- `backend/`: API REST em Spring Boot
- `frontend/`: SPA em React + Vite

Além do CRUD de usuários, amizades, projetos, tarefas e sprints, a aplicação agora inclui:

- cache com Redis para leituras e resolução de identidade
- mensageria com RabbitMQ para eventos de autenticação, segurança e projeto
- background services agendados para manutenção operacional
- endurecimento de autenticação com política de senha, controle de tentativas, invalidação e versionamento de token

## Arquitetura

### Backend

- Java 17 + Spring Boot 3.3
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- Spring Cache + Redis
- Spring AMQP + RabbitMQ
- tarefas agendadas com `@Scheduled`

### Frontend

- React 19 + TypeScript + Vite
- React Query
- React Hook Form + Zod
- Tailwind CSS

## Principais recursos

### Segurança

- senhas com BCrypt
- política de senha forte:
  - mínimo de 12 caracteres
  - letra maiúscula
  - letra minúscula
  - número
  - caractere especial
  - rejeição de senhas com partes óbvias do nome ou email
- bloqueio temporário após repetidas falhas de login
- JWT com `tokenVersion` e `jti`
- endpoint de logout com blacklist de token
- rotação de versão do token ao redefinir senha
- cabeçalhos HTTP de segurança básicos no backend

### Cache com Redis

- cache para dados de usuário autenticado
- cache para consultas frequentes de usuário, projeto, convites, tarefas, sprints e amizades
- invalidação programática após mutações

### Mensageria com RabbitMQ

- publicação de eventos para:
  - cadastro
  - login
  - logout
  - falhas de autenticação
  - rotação de senha
  - criação/atualização/remoção de projeto
  - criação/atualização/remoção de tarefa e sprint
  - convites de projeto

### Background services

- limpeza periódica de blacklist local de tokens
- desbloqueio automático de contas após expirar o lock temporário
- lembretes de expiração de senha por eventos assíncronos
- lembretes de convites de projeto pendentes

## Estrutura

```text
Scrum-Manager/
├── backend/
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── package.json
│   └── src/
└── README.md
```

## Pré-requisitos

- Java 17+
- Maven 3.9+
- Node.js 20+
- npm 10+
- PostgreSQL 16+
- Redis 7+ (recomendado para cache e blacklist)
- RabbitMQ 3.13+ (recomendado para eventos)

## Configuração rápida

### 1. Banco de dados

Crie o banco:

```sql
CREATE DATABASE scrum_manager;
```

Depois execute o schema inicial:

```bash
psql -U postgres -d scrum_manager -f backend/src/main/resources/schema.sql
```

### 2. Backend

```bash
cd backend
mvn spring-boot:run
```

### 3. Frontend

```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

## Variáveis de ambiente principais

### Backend

| Variável | Descrição | Padrão |
| --- | --- | --- |
| `DB_USERNAME` | usuário do PostgreSQL | `postgres` |
| `DB_PASSWORD` | senha do PostgreSQL | `postgres` |
| `JWT_SECRET` | segredo JWT em Base64 | exemplo local |
| `REDIS_HOST` | host do Redis | `localhost` |
| `REDIS_PORT` | porta do Redis | `6379` |
| `RABBITMQ_HOST` | host do RabbitMQ | `localhost` |
| `RABBITMQ_PORT` | porta do RabbitMQ | `5672` |
| `RABBITMQ_USERNAME` | usuário do RabbitMQ | `guest` |
| `RABBITMQ_PASSWORD` | senha do RabbitMQ | `guest` |

### Frontend

| Variável | Descrição |
| --- | --- |
| `VITE_API_URL` | URL base da API |

## Filas e exchange

- exchange: `scrum-manager.events`
- fila auth: `scrum-manager.auth.events`
- fila project: `scrum-manager.project.events`
- fila security: `scrum-manager.security.events`

## Endpoints de autenticação

| Método | Rota | Descrição |
| --- | --- | --- |
| `POST` | `/api/auth/register` | cria conta |
| `POST` | `/api/auth/login` | autentica e retorna JWT |
| `POST` | `/api/auth/logout` | invalida o token atual |
| `POST` | `/api/auth/activate` | redefine senha e reativa conta |

## Validação e build

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm run lint
npm run build
```

## Observações operacionais

- em produção, troque o `JWT_SECRET`
- configure Redis para aproveitar cache distribuído e blacklist persistente de tokens
- configure RabbitMQ para consumo dos eventos por serviços externos ou workers internos
- o profile de teste usa cache em memória simples e desativa listeners AMQP

## Documentação específica

- backend: [`/backend/README.md`](./backend/README.md)
- frontend: [`/frontend/README.md`](./frontend/README.md)
