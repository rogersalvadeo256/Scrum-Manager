# Scrum Manager — Agent Specification

> Arquivo lido por agentes de IA (GitHub Copilot Antigravity, OpenAI Codex, Gemini Code Assist, etc.)
> para orientar geração de código, revisões e automações neste repositório.

---

## Visão geral

Plataforma full-stack de gestão de projetos Scrum.

| Camada    | Stack principal                                         |
|-----------|---------------------------------------------------------|
| Backend   | Java 17 · Spring Boot 3.3 · Spring Security · JWT       |
| Banco     | PostgreSQL 16 + Hibernate / Spring Data JPA             |
| Cache     | Redis 7 (Spring Cache)                                  |
| Mensageria| RabbitMQ 3.13 (Spring AMQP)                             |
| Frontend  | React 19 · TypeScript · Vite · React Query · Zod        |

---

## Estrutura de diretórios

```
Scrum-Manager/
├── backend/          ← API REST Spring Boot
│   └── src/main/java/com/scrummanager/
│       ├── config/         (SecurityConfig, CacheConfig, MessagingConfig, …)
│       ├── controller/     (AuthController, ProjectController, TaskController, …)
│       ├── domain/
│       │   ├── entity/     (User, Project, ProjectMember, ProjectTask, ProjectSprint, …)
│       │   └── enums/      (AccountStatus, ProjectStatus, TaskStatus, …)
│       ├── dto/            (request/, response/)
│       ├── repository/     (Spring Data JPA interfaces)
│       ├── security/       (JwtTokenProvider, JwtAuthenticationFilter, PasswordPolicyValidator, …)
│       └── service/        (AuthService, ProjectService, TaskService, …)
└── frontend/         ← SPA React
    └── src/
        ├── api/            (http.ts, auth.ts, projects.ts, tasks.ts, …)
        ├── features/
        ├── pages/
        └── types/
```

---

## Comandos de build e teste

### Backend

```bash
cd backend
mvn compile          # compilar
mvn test             # rodar testes (usa profile 'test': cache simples, AMQP desativado)
mvn spring-boot:run  # iniciar servidor na porta 8080
```

### Frontend

```bash
cd frontend
pnpm install
pnpm run lint        # ESLint
pnpm run build       # build de produção
pnpm run dev         # servidor de desenvolvimento na porta 5173
```

---

## Invariantes do sistema

As invariantes abaixo **nunca devem ser violadas** por nenhum agente ou PR.

### Segurança de credenciais

1. **Senhas são sempre armazenadas como hash BCrypt(12).** O campo `password_hash` jamais recebe texto simples.
2. **Respostas de segurança são sempre armazenadas como hash BCrypt.** O campo `security_answer_hash` jamais recebe texto simples. Comparações usam `passwordEncoder.matches()`.
3. **Nenhum segredo (JWT secret, senhas de BD, credenciais de broker) deve ser commitado no código.** Toda configuração sensível vem de variáveis de ambiente.

### Autenticação e tokens JWT

4. **O token JWT carrega `uid` (userId), `ver` (tokenVersion) e `jti` (UUID único por token).**
5. **`tokenVersion` é verificado em toda requisição autenticada.** Um mismatch invalida o token sem consultar o banco.
6. **Todo token que passou por logout é adicionado à blacklist** (Redis preferencial, fallback em memória). `TokenStateService.isBlacklisted()` é sempre chamado na validação.
7. **Ao redefinir senha (`activateAccount`), `tokenVersion` é incrementado**, invalidando todos os tokens ativos do usuário.
8. **O JWT secret em `application.yml` é apenas um placeholder de desenvolvimento.** Em produção, `JWT_SECRET` deve ser definido como variável de ambiente com segredo forte em Base64 (≥256 bits).

### Política de senha

9. **Toda nova senha — no cadastro e na redefinição — passa por `PasswordPolicyValidator.validate()`.**
   - Mínimo de 12 caracteres (configurável via `APP_PASSWORD_MIN_LENGTH`).
   - Pelo menos uma letra maiúscula, uma minúscula, um dígito e um caractere especial.
   - Não pode conter partes do `username`, `email` ou nome do perfil (fragmento ≥ 3 chars).

### Bloqueio de conta

10. **Após `maxAttempts` falhas consecutivas de login (padrão: 5), a conta é bloqueada por `durationMinutes` minutos (padrão: 15).** O desbloqueio é automático via `BackgroundMaintenanceService`.

### Autorização e isolamento de dados

11. **Somente o criador de um projeto pode atualizá-lo, excluí-lo ou convidar membros.** (`findAndAuthorize` em `ProjectService`)
12. **Somente o criador de uma tarefa pode editá-la ou excluí-la.**
13. **Leitura de tarefas e sprints de um projeto exige que o solicitante seja criador ou membro aceito do projeto.** Qualquer endpoint de leitura de tarefas/sprints deve chamar `assertProjectAccess(projectId, userId)` antes de retornar dados.
14. **Um usuário não pode enviar convite de amizade para si mesmo.**
15. **Apenas o destinatário de uma solicitação de amizade pode aceitá-la ou recusá-la.**
16. **Apenas o destinatário de um convite de projeto pode respondê-lo.**

### Exclusão de projetos

17. **Projetos são excluídos de forma soft (status = `DELETED`), nunca removidos fisicamente.** Consultas sempre filtram com `statusNot(DELETED)`.

### Cache

18. **Toda mutação que afeta dados cacheados chama o método correspondente em `CacheInvalidationService` antes de retornar.** Nunca deixar cache desatualizado após escrita.
19. **Chaves de cache que dependem de autorização por usuário incluem `userId` na chave** (ex.: `project-mine:#userId`). Cache de projeto-tarefas deve incluir tanto `projectId` quanto `userId` quando há verificação de acesso.

### Mensageria

20. **Todo evento de domínio significativo é publicado via `DomainEventPublisher.publish()`.** A lista mínima cobre: registro, login, logout, falha de login, rotação de senha, criação/atualização/remoção de projeto, tarefa e sprint, convites.

### HTTP e CORS

21. **CORS permite apenas origens explicitamente configuradas** (`APP_CORS_ALLOWED_ORIGINS`). Nunca usar `*` em produção.
22. **Cabeçalhos HTTP obrigatórios em produção**: `X-Frame-Options: DENY`, `X-Content-Type-Options: nosniff`, `Referrer-Policy: no-referrer`, `Strict-Transport-Security` (adicionar via proxy/load balancer em produção).

---

## Convenções de código

### Backend (Java)

- Entidades JPA usam Lombok (`@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`).
- DTOs são `record` Java quando imutáveis (requests e responses).
- Controllers retornam `ResponseEntity<T>` com status HTTP explícito.
- Services são anotados com `@Transactional` nas mutações e `@Transactional(readOnly = true)` nas leituras.
- Exceções de negócio: `IllegalArgumentException` (not found / bad input), `IllegalStateException` (conflito de estado), `AccessDeniedException` (autorização).
- `GlobalExceptionHandler` mapeia essas exceções para respostas HTTP apropriadas.
- Testes unitários usam `MockitoExtension` e instanciam o service manualmente em `@BeforeEach`.

### Frontend (TypeScript/React)

- Chamadas de API ficam em `src/api/` e usam o cliente Axios em `http.ts`.
- Tipos de API ficam em `src/types/api.ts`.
- Formulários usam React Hook Form + Zod para validação no cliente.
- Queries e mutations usam React Query.
- Estilos com Tailwind CSS.

---

## Variáveis de ambiente críticas (produção)

| Variável                  | Descrição                                        |
|---------------------------|--------------------------------------------------|
| `JWT_SECRET`              | Segredo JWT Base64 ≥ 256 bits — **obrigatório**  |
| `DB_USERNAME`             | Usuário PostgreSQL                               |
| `DB_PASSWORD`             | Senha PostgreSQL — **nunca usar padrão `postgres`** |
| `REDIS_PASSWORD`          | Senha Redis                                      |
| `RABBITMQ_USERNAME`       | Usuário RabbitMQ — **nunca usar padrão `guest`** |
| `RABBITMQ_PASSWORD`       | Senha RabbitMQ — **nunca usar padrão `guest`**   |
| `APP_CORS_ALLOWED_ORIGINS`| Origens CORS permitidas                          |

---

## Padrões a evitar (antipadrões)

- ❌ Não armazenar senha ou resposta de segurança em texto simples.
- ❌ Não pular `assertProjectAccess` / `findAndAuthorize` em endpoints de leitura de dados sensíveis.
- ❌ Não usar `allowedOrigins("*")` em CorsConfiguration.
- ❌ Não commitar credenciais ou segredos no código-fonte.
- ❌ Não retornar `password_hash` ou `security_answer_hash` em qualquer DTO de resposta.
- ❌ Não usar `ddl-auto: create` ou `ddl-auto: update` em produção — usar Flyway/Liquibase.
- ❌ Não chamar `System.exit()` ou bloquear a thread principal fora de contexto de teste.
- ❌ Não colocar lógica de negócio em controllers — manter nos services.
