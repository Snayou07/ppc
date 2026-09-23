-- Пользователи системы
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'MANAGER', -- ADMIN / MANAGER
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Рекламные платформы (справочник)
CREATE TABLE platforms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE  -- Google Ads / Meta Ads
);

-- Рекламные аккаунты клиентов
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    platform_id BIGINT NOT NULL REFERENCES platforms(id),
    external_account_id VARCHAR(100), -- id аккаунта в Google/Meta
    owner_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Кампании
CREATE TABLE campaigns (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    platform_id BIGINT NOT NULL REFERENCES platforms(id),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE / PAUSED / COMPLETED
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Еженедельные метрики кампании (данные из CSV)
CREATE TABLE weekly_metrics (
    id BIGSERIAL PRIMARY KEY,
    campaign_id BIGINT NOT NULL REFERENCES campaigns(id) ON DELETE CASCADE,
    week_start_date DATE NOT NULL,
    impressions BIGINT NOT NULL DEFAULT 0,
    clicks BIGINT NOT NULL DEFAULT 0,
    conversions BIGINT NOT NULL DEFAULT 0,
    spend NUMERIC(12,2) NOT NULL DEFAULT 0,
    revenue NUMERIC(12,2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (campaign_id, week_start_date)
);

-- Лог импортов CSV
CREATE TABLE import_logs (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    imported_by BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(20) NOT NULL, -- SUCCESS / FAILED / PARTIAL
    rows_processed INT NOT NULL DEFAULT 0,
    rows_failed INT NOT NULL DEFAULT 0,
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Сгенерированные отчёты
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    generated_by BIGINT NOT NULL REFERENCES users(id),
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    format VARCHAR(10) NOT NULL, -- PDF / CSV
    best_campaign_id BIGINT REFERENCES campaigns(id),
    worst_campaign_id BIGINT REFERENCES campaigns(id),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO platforms (name) VALUES ('Google Ads'), ('Meta Ads');
