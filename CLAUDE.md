# Scrum Manager — Claude Code Spec

> Lido automaticamente pelo Claude Code ao abrir o projeto.

---

## Resumo do projeto

Plataforma full-stack de gestão de projetos Scrum.

- **Backend**: Java 17 + Spring Boot 3.3, Spring Security, JWT, JPA/PostgreSQL, Redis, RabbitMQ
- **Frontend**: React 19 + TypeScript + Vite + React Query + Zod + Tailwind CSS
- **Documentação interativa da API**: Scalar em `http://localhost:8080/docs`

---

## Comandos essenciais

```bash
# Backend
cd backend && mvn compile
cd backend && mvn test
cd backend && mvn spring-boot:run

# Frontend
cd frontend && pnpm install
cd frontend && pnpm run lint
cd frontend && pnpm run build
cd frontend && pnpm run dev
```

Testes do backend rodam com o profile `test` (cache simples em memória, listeners AMQP desativados). Não é necessário Redis ou RabbitMQ disponíveis para executar `mvn test`.

---

## Arquitetura

```
backend/src/main/java/com/scrummanager/
  config/      → SecurityConfig, CacheConfig, MessagingConfig, AppSecurityProperties
  controller/  → REST controllers (delegam para services; retornam ResponseEntity<T>)
  domain/
    entity/    → JPA entities: User, Project, ProjectMember, ProjectTask, ProjectSprint, Friendship, UserProfile
    enums/     → AccountStatus, ProjectStatus, TaskStatus, SprintStatus, RequestStatus, Availability
  dto/         → Records imutáveis: request/* e response/*
  repository/  → Spring Data JPA
  security/    → JwtTokenProvider, JwtAuthenticationFilter, PasswordPolicyValidator, UserDetailsServiceImpl
  service/     → AuthService, ProjectService, TaskService, FriendshipService, UserService,
                  CacheInvalidationService, TokenStateService, DomainEventPublisher,
                  BackgroundMaintenanceService

frontend/src/
  api/         → http.ts (Axios client) + módulos por domínio (auth, projects, tasks, …)
  types/       → api.ts (tipos TypeScript dos contratos da API)
  features/    → slices de funcionalidade
  pages/       → AuthPage, DashboardPage, ProjectPage
  components/  → componentes reutilizáveis
```

---

## Invariantes obrigatórias

Estas regras **nunca devem ser violadas**:

### Credenciais
1. Senhas armazenadas **apenas** como hash BCrypt(12) — campo `password_hash`.
2. Respostas de segurança armazenadas **apenas** como hash BCrypt — campo `security_answer_hash`.
3. Nenhum segredo nos arquivos commitados; sempre via variáveis de ambiente.

### Tokens JWT
4. Token carrega `uid`, `ver` (tokenVersion), `jti` (UUID único).
5. `tokenVersion` é verificado em toda requisição autenticada; mismatch invalida o token.
6. Token de logout vai para a blacklist em `TokenStateService` (Redis ou fallback em memória).
7. Redefinição de senha incrementa `tokenVersion`, invalidando todos os tokens ativos.

### Política de senha
8. Toda nova senha passa por `PasswordPolicyValidator.validate()` — mín. 12 chars, maiúscula, minúscula, número, especial, sem fragmento pessoal.

### Autorização
9. Criador do projeto: único que pode atualizar, deletar e convidar membros.
10. Criador da tarefa: único que pode editar e excluir.
11. **Leitura de tarefas e sprints exige verificação de acesso ao projeto** (`assertProjectAccess(projectId, userId)`) — não retornar dados para não-membros.
12. Self-friendship bloqueado. Só destinatário responde convites.

### Dados
13. Projetos são soft-deleted (`status = DELETED`). Nunca hard-delete.
14. Toda mutação chama `CacheInvalidationService` para evitar cache stale.
15. Todo evento significativo é publicado via `DomainEventPublisher.publish()`.

---

## Convenções de código

### Backend
- Entities: Lombok (`@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor`)
- DTOs: Java `record`
- Controllers: `ResponseEntity<T>` com status HTTP explícito; sem lógica de negócio
- Services: `@Transactional` em mutações, `@Transactional(readOnly = true)` em leituras
- Exceções: `IllegalArgumentException` (not found), `IllegalStateException` (conflito), `AccessDeniedException` (autorização)
- Testes: `@ExtendWith(MockitoExtension.class)`, service instanciado manualmente em `@BeforeEach`

### Frontend
- Chamadas de API em `src/api/` usando `http.ts`
- Formulários: React Hook Form + Zod
- Estado remoto: React Query
- Estilos: Tailwind CSS

---

## Antipadrões proibidos

- ❌ Senha ou resposta de segurança em texto simples
- ❌ Endpoint de leitura sem verificação de autorização de acesso ao projeto
- ❌ `allowedOrigins("*")` em CorsConfiguration
- ❌ Credenciais ou segredos commitados
- ❌ Retornar `password_hash` ou `security_answer_hash` em resposta de API
- ❌ `ddl-auto: create/update` em produção
- ❌ Lógica de negócio em controllers
