CREATE SCHEMA IF NOT EXISTS notification;

CREATE TABLE notification.notifications (
                                            id UUID PRIMARY KEY,

                                            recipient_user_id UUID NOT NULL,

                                            title VARCHAR(200) NOT NULL,
                                            message VARCHAR(1000) NOT NULL,
                                            type VARCHAR(100) NOT NULL,

                                            source_service VARCHAR(100),
                                            source_resource_type VARCHAR(100),
                                            source_resource_id UUID,

                                            is_read BOOLEAN NOT NULL DEFAULT FALSE,

                                            created_at TIMESTAMP NOT NULL,
                                            read_at TIMESTAMP
);

CREATE INDEX idx_notifications_recipient_user_id
    ON notification.notifications(recipient_user_id);

CREATE INDEX idx_notifications_recipient_read
    ON notification.notifications(recipient_user_id, is_read);

CREATE INDEX idx_notifications_created_at
    ON notification.notifications(created_at);

CREATE INDEX idx_notifications_source
    ON notification.notifications(source_service, source_resource_type, source_resource_id);