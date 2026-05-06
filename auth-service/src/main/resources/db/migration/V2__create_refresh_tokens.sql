CREATE TABLE auth.refresh_tokens (
                                     id UUID PRIMARY KEY,

                                     user_id UUID NOT NULL,
                                     token_hash VARCHAR(255) NOT NULL UNIQUE,

                                     expires_at TIMESTAMP NOT NULL,
                                     revoked_at TIMESTAMP,
                                     created_at TIMESTAMP NOT NULL,

                                     CONSTRAINT fk_refresh_tokens_user
                                         FOREIGN KEY (user_id)
                                             REFERENCES auth.users(id)
);

CREATE INDEX idx_refresh_tokens_user_id
    ON auth.refresh_tokens(user_id);

CREATE INDEX idx_refresh_tokens_token_hash
    ON auth.refresh_tokens(token_hash);

CREATE INDEX idx_refresh_tokens_active_user
    ON auth.refresh_tokens(user_id, revoked_at, expires_at);