CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE IF NOT EXISTS auth.users (
                                          id UUID PRIMARY KEY,
                                          email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    failed_login_count INTEGER NOT NULL DEFAULT 0,
    locked_until TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
    );

CREATE INDEX IF NOT EXISTS idx_auth_users_email
    ON auth.users(email);

CREATE INDEX IF NOT EXISTS idx_auth_users_deleted_at
    ON auth.users(deleted_at);