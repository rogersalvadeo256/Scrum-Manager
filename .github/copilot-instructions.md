# Scrum Manager — GitHub Copilot Instructions

Plataforma full-stack de gestão de projetos Scrum: **backend** Java 17 + Spring Boot 3.3 em `backend/` e **frontend** React 19 + TypeScript + Vite em `frontend/`.

---

## Build & test

```bash
# Backend
cd backend && mvn test        # profile 'test': cache simples, sem RabbitMQ
cd backend && mvn spring-boot:run

# Frontend
cd frontend && pnpm run lint
cd frontend && pnpm run build
```

---

## Regras invioláveis (invariantes)

- **Senhas e respostas de segurança sempre como hash BCrypt** — nunca em texto simples.
- **JWT**: todo token carrega `uid`, `ver` (tokenVersion), `jti`. A validação verifica todos os três e checa a blacklist em `TokenStateService`.
- **Redefinição de senha incrementa `tokenVersion`**, invalidando todos os tokens anteriores.
- **Toda nova senha passa por `PasswordPolicyValidator.validate()`** (≥12 chars, maiúscula, minúscula, número, especial, sem fragmento pessoal).
- **Após 5 falhas de login, conta bloqueada por 15 min** (configurável via `AppSecurityProperties`).
- **`GET /api/projects/{id}/tasks` e `GET /api/projects/{id}/sprints`** exigem que o solicitante seja criador ou membro aceito do projeto — chamar `assertProjectAccess(projectId, userId)`.
- **Somente criador do projeto** pode atualizá-lo, deletá-lo e convidar membros.
- **Somente criador da tarefa** pode editá-la e excluí-la.
- **Projetos são soft-deleted** (`status = DELETED`), nunca removidos fisicamente.
- **Toda mutação invalida o cache** via `CacheInvalidationService`.
- **Nenhum segredo commitado** no repositório — tudo via variáveis de ambiente.
- **Nunca retornar** `password_hash` ou `security_answer_hash` em respostas de API.

---

## Estrutura de pacotes (backend)

```
com.scrummanager.
  config/       SecurityConfig, CacheConfig, MessagingConfig, AppSecurityProperties
  controller/   REST — delegam para services, retornam ResponseEntity<T>
  domain/
    entity/     User, Project, ProjectMember, ProjectTask, ProjectSprint, Friendship
    enums/      AccountStatus, ProjectStatus, TaskStatus, SprintStatus, RequestStatus
  dto/          Records Java: request/* e response/*
  repository/   Spring Data JPA
  security/     JwtTokenProvider, JwtAuthenticationFilter, PasswordPolicyValidator
  service/      AuthService, ProjectService, TaskService, FriendshipService,
                CacheInvalidationService, TokenStateService, DomainEventPublisher
```

---

## Convenções

- **Entities**: Lombok (`@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor`).
- **DTOs**: `record` Java.
- **Controllers**: sem lógica de negócio; retornam `ResponseEntity<T>`.
- **Services**: `@Transactional` em mutações, `@Transactional(readOnly = true)` em leituras.
- **Exceções**: `IllegalArgumentException` (not found), `IllegalStateException` (conflito), `AccessDeniedException` (autorização) — capturadas pelo `GlobalExceptionHandler`.
- **Frontend**: chamadas de API em `src/api/`, formulários com React Hook Form + Zod, estado remoto com React Query.
