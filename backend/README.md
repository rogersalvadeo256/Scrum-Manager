# Scrum Manager – Backend API

REST API migrado do desktop JavaFX para **Spring Boot 3 + Java 17 + PostgreSQL**, com suporte a Redis, RabbitMQ e serviços de manutenção agendados.

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 (LTS) |
| Framework | Spring Boot 3.3 |
| Banco de dados | PostgreSQL 16 |
| ORM | Spring Data JPA / Hibernate 6 |
| Segurança | Spring Security 6 + JWT (JJWT 0.12) + BCrypt |
| Cache | Spring Cache + Redis |
| Mensageria | Spring AMQP + RabbitMQ |
| Build | Maven |

## Mudanças em relação ao projeto original

| Antes | Depois |
|---|---|
| JavaFX (desktop monolítico) | REST API desacoplada |
| MySQL | PostgreSQL |
| Hibernate `EntityManager` manual | Spring Data JPA (`JpaRepository`) |
| Senhas em texto plano | BCrypt (strength 12) |
| Sem autenticação via API | JWT stateless + Spring Security |
| Java (versão antiga) | Java 17 |

## Estrutura

```
backend/
├── pom.xml
└── src/
    └── main/
        ├── java/com/scrummanager/
        │   ├── ScrumManagerApplication.java
        │   ├── config/          # SecurityConfig, GlobalExceptionHandler
        │   ├── domain/
        │   │   ├── entity/      # User, UserProfile, Project, ProjectTask,
        │   │   │                #   ProjectSprint, ProjectMember, Friendship
        │   │   └── enums/       # AccountStatus, RequestStatus, …
        │   ├── repository/      # Spring Data JPA interfaces
        │   ├── service/         # AuthService, ProjectService, TaskService, …
        │   ├── controller/      # REST controllers
        │   ├── dto/             # request / response records
        │   └── security/        # JwtTokenProvider, JwtAuthenticationFilter,
        │                        #   UserDetailsServiceImpl
        └── resources/
            ├── application.yml
            ├── application-test.yml
            └── schema.sql       # PostgreSQL DDL (use Flyway in production)
```

## Pré-requisitos

* Java 17
* PostgreSQL 16 rodando localmente (ou via Docker)
* Redis 7 (recomendado para cache e blacklist)
* RabbitMQ 3.13 (recomendado para eventos)
* Maven 3.9+

## Configuração

### Banco de dados

```sql
CREATE DATABASE scrum_manager;
```

Execute o script `src/main/resources/schema.sql` uma vez para criar as tabelas.

### Variáveis de ambiente

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_USERNAME` | `postgres` | Usuário do PostgreSQL |
| `DB_PASSWORD` | `postgres` | Senha do PostgreSQL |
| `JWT_SECRET` | *(base64 de exemplo)* | Chave secreta JWT em Base64 – **troque em produção!** |
| `REDIS_HOST` | `localhost` | Host do Redis |
| `REDIS_PORT` | `6379` | Porta do Redis |
| `RABBITMQ_HOST` | `localhost` | Host do RabbitMQ |
| `RABBITMQ_PORT` | `5672` | Porta do RabbitMQ |
| `RABBITMQ_USERNAME` | `guest` | Usuário do RabbitMQ |
| `RABBITMQ_PASSWORD` | `guest` | Senha do RabbitMQ |

Gere um segredo seguro:

```bash
openssl rand -base64 32
```

## Rodar

```bash
cd backend
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Principais endpoints

### Autenticação (`/api/auth`)

| Método | Path | Descrição |
|---|---|---|
| POST | `/api/auth/register` | Cadastro |
| POST | `/api/auth/login` | Login – retorna JWT |
| POST | `/api/auth/logout` | Invalida o token atual |
| POST | `/api/auth/activate` | Reativa conta inativa via pergunta de segurança |

### Usuários (`/api/users`)

| Método | Path | Descrição |
|---|---|---|
| GET | `/api/users/{id}` | Perfil de usuário |
| GET | `/api/users/search?name=` | Busca por nome |
| PATCH | `/api/users/{id}/photo` | Atualiza foto |

### Projetos (`/api/projects`)

| Método | Path | Descrição |
|---|---|---|
| POST | `/api/projects` | Criar projeto |
| GET | `/api/projects/mine` | Meus projetos (criador) |
| GET | `/api/projects/member` | Projetos em que sou membro |
| PUT | `/api/projects/{id}` | Editar projeto |
| DELETE | `/api/projects/{id}` | Arquivar projeto |
| POST | `/api/projects/{id}/invite/{userId}` | Convidar membro |
| PATCH | `/api/projects/invites/{memberId}` | Aceitar/Recusar convite |

### Tarefas e Sprints (`/api/projects/{projectId}/tasks|sprints`)

CRUD completo de tasks e sprints por projeto.

### Amizades (`/api/friendships`)

| Método | Path | Descrição |
|---|---|---|
| POST | `/api/friendships/request/{userId}` | Enviar solicitação |
| PATCH | `/api/friendships/{id}/answer` | Aceitar/Recusar |
| DELETE | `/api/friendships/{id}` | Remover amigo |
| GET | `/api/friendships/pending` | Solicitações pendentes |
| GET | `/api/friendships` | Lista de amigos |

## Segurança

* Todas as senhas são armazenadas com **BCrypt** (strength 12).
* A resposta de segurança também é armazenada com **BCrypt**.
* Política de senha: mínimo de 12 caracteres, com maiúscula, minúscula, número e caractere especial.
* Autenticação via **JWT** (HS256, 24 h por padrão) com `tokenVersion` e `jti`.
* Logout invalida o token atual e redefinição de senha faz rotação da versão do token.
* Falhas repetidas de login bloqueiam a conta temporariamente.
* Endpoints públicos: apenas `/api/auth/**`.
* CORS configurável via `SecurityConfig`.
* Em produção, configure `JWT_SECRET` com uma chave forte e o DDL como `validate`.

## Eventos assíncronos

A API publica eventos na exchange `scrum-manager.events` com routing keys como:

- `auth.user.registered`
- `auth.user.logged-in`
- `auth.user.logged-out`
- `security.login.failed`
- `security.password.rotated`
- `project.created`
- `project.member.invited`
- `project.task.created`

## Background services

Serviços agendados incluídos:

- limpeza do estado local de blacklist de tokens
- desbloqueio automático de contas após o lock expirar
- lembretes de expiração de senha
- lembretes de convites de projeto pendentes
