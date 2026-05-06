CREATE TABLE auth.password_reset_tokens (
                                            id UUID PRIMARY KEY,

                                            user_id UUID NOT NULL,
                                            token_hash VARCHAR(255) NOT NULL UNIQUE,

                                            expires_at TIMESTAMP NOT NULL,
                                            used_at TIMESTAMP,
                                            created_at TIMESTAMP NOT NULL,

                                            CONSTRAINT fk_password_reset_tokens_user
                                                FOREIGN KEY (user_id)
                                                    REFERENCES auth.users(id)
);

CREATE INDEX idx_password_reset_tokens_user_id
    ON auth.password_reset_tokens(user_id);

CREATE INDEX idx_password_reset_tokens_token_hash
    ON auth.password_reset_tokens(token_hash);

CREATE INDEX idx_password_reset_tokens_usable
    ON auth.password_reset_tokens(user_id, used_at, expires_at);