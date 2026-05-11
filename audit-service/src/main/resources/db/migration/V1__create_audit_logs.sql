CREATE SCHEMA IF NOT EXISTS audit;

CREATE TABLE audit.audit_logs (
                                  id UUID PRIMARY KEY,

                                  source_service VARCHAR(100) NOT NULL,

                                  actor_user_id UUID,

                                  action VARCHAR(100) NOT NULL,

                                  resource_type VARCHAR(100) NOT NULL,
                                  resource_id UUID,

                                  description VARCHAR(1000),

                                  created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_audit_logs_source_service
    ON audit.audit_logs(source_service);

CREATE INDEX idx_audit_logs_actor_user_id
    ON audit.audit_logs(actor_user_id);

CREATE INDEX idx_audit_logs_action
    ON audit.audit_logs(action);

CREATE INDEX idx_audit_logs_resource
    ON audit.audit_logs(resource_type, resource_id);

CREATE INDEX idx_audit_logs_created_at
    ON audit.audit_logs(created_at);