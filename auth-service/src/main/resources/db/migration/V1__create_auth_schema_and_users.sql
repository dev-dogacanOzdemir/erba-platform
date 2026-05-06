CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE auth.users (
                            id UUID PRIMARY KEY,

                            email VARCHAR(255) NOT NULL UNIQUE,
                            password_hash VARCHAR(255) NOT NULL,

                            role VARCHAR(50) NOT NULL,
                            status VARCHAR(50) NOT NULL,

                            failed_login_count INTEGER NOT NULL DEFAULT 0,
                            locked_until TIMESTAMP,

                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL,
                            deleted_at TIMESTAMP
);

CREATE INDEX idx_auth_users_email
    ON auth.users(email);

CREATE INDEX idx_auth_users_status
    ON auth.users(status);