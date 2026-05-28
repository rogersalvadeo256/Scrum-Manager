-- =============================================================================
-- Scrum Manager – PostgreSQL schema
-- Generated from the legacy MySQL dump, adapted for PostgreSQL + Spring Boot 3
-- Run once before starting the application (or integrate with Flyway/Liquibase)
-- =============================================================================

-- ── user_profile ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS user_profile (
    prof_cod          BIGSERIAL PRIMARY KEY,
    prof_name         VARCHAR(255) NOT NULL,
    prof_availability VARCHAR(50)  NOT NULL DEFAULT 'AVAILABLE',
    prof_photo        BYTEA
);

-- ── users ─────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    user_cod              BIGSERIAL PRIMARY KEY,
    username              VARCHAR(50)  NOT NULL UNIQUE,
    email                 VARCHAR(255) NOT NULL UNIQUE,
    password_hash         VARCHAR(255) NOT NULL,
    security_question     TEXT         NOT NULL,
    security_answer_hash  VARCHAR(255) NOT NULL,
    registration_date     DATE         NOT NULL,
    account_status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    token_version         INT          NOT NULL DEFAULT 0,
    failed_login_attempts INT          NOT NULL DEFAULT 0,
    account_locked_until  TIMESTAMP,
    last_password_change_at TIMESTAMP,
    password_expires_at   TIMESTAMP,
    last_login_at         TIMESTAMP,
    profile_id            BIGINT       NOT NULL REFERENCES user_profile(prof_cod) ON DELETE CASCADE
);

-- ── project ──────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS project (
    proj_cod         BIGSERIAL PRIMARY KEY,
    proj_name        VARCHAR(255) NOT NULL,
    proj_description TEXT,
    proj_creator_id  BIGINT       NOT NULL REFERENCES users(user_cod),
    proj_date_start  DATE         NOT NULL,
    proj_status      VARCHAR(50)  NOT NULL DEFAULT 'IN_PROGRESS',
    proj_type        VARCHAR(100)
);

-- ── project_member ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS project_member (
    mbr_cod                  BIGSERIAL PRIMARY KEY,
    mbr_invited_by_id        BIGINT      NOT NULL REFERENCES users(user_cod),
    mbr_user_id              BIGINT      NOT NULL REFERENCES users(user_cod),
    mbr_project_id           BIGINT      NOT NULL REFERENCES project(proj_cod) ON DELETE CASCADE,
    mbr_invite_sent_date     DATE,
    mbr_invite_answered_date DATE,
    mbr_invite_status        VARCHAR(20) NOT NULL DEFAULT 'ON_HOLD',
    mbr_status               VARCHAR(20),
    mbr_function             VARCHAR(100),
    mbr_permissions          VARCHAR(255),
    mbr_scrum_master         BOOLEAN     NOT NULL DEFAULT FALSE,
    UNIQUE (mbr_project_id, mbr_user_id)
);

-- ── project_task ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS project_task (
    task_cod       BIGSERIAL PRIMARY KEY,
    task_title     VARCHAR(255) NOT NULL,
    task_text      TEXT,
    task_points    INT          NOT NULL DEFAULT 0,
    task_creator_id BIGINT     NOT NULL REFERENCES users(user_cod),
    task_executor_id BIGINT    REFERENCES users(user_cod),
    task_status    VARCHAR(20) NOT NULL DEFAULT 'TO_DO',
    proj_cod       BIGINT      NOT NULL REFERENCES project(proj_cod) ON DELETE CASCADE,
    task_date_start DATE
);

-- ── project_sprint ────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS project_sprint (
    sprint_cod      BIGSERIAL PRIMARY KEY,
    sprint_title    VARCHAR(255) NOT NULL,
    sprint_text     TEXT,
    proj_sprint_cod BIGINT      NOT NULL REFERENCES project(proj_cod) ON DELETE CASCADE,
    sprint_points   INT         NOT NULL DEFAULT 0,
    sprint_status   VARCHAR(20) NOT NULL DEFAULT 'DOING'
);

-- ── friendship ────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS friendship (
    frq_id             BIGSERIAL PRIMARY KEY,
    frq_requested_by_id BIGINT     NOT NULL REFERENCES users(user_cod),
    frq_receiver_id     BIGINT     NOT NULL REFERENCES users(user_cod),
    frq_status          VARCHAR(20) NOT NULL DEFAULT 'ON_HOLD',
    frq_sent_date       DATE        NOT NULL
);

-- ── Indexes ───────────────────────────────────────────────────────────────────
CREATE INDEX IF NOT EXISTS idx_project_creator   ON project(proj_creator_id);
CREATE INDEX IF NOT EXISTS idx_mbr_user          ON project_member(mbr_user_id);
CREATE INDEX IF NOT EXISTS idx_mbr_project       ON project_member(mbr_project_id);
CREATE INDEX IF NOT EXISTS idx_task_project      ON project_task(proj_cod);
CREATE INDEX IF NOT EXISTS idx_sprint_project    ON project_sprint(proj_sprint_cod);
CREATE INDEX IF NOT EXISTS idx_frq_receiver      ON friendship(frq_receiver_id);
CREATE INDEX IF NOT EXISTS idx_frq_requester     ON friendship(frq_requested_by_id);
