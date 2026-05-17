CREATE SCHEMA IF NOT EXISTS purchase;

CREATE TABLE purchase.purchase_requests (
                                            id UUID PRIMARY KEY,

                                            requester_user_id UUID NOT NULL,

                                            product_name VARCHAR(255) NOT NULL,
                                            quantity INTEGER NOT NULL,
                                            description VARCHAR(2000),

                                            status VARCHAR(50) NOT NULL,

                                            reviewed_by_user_id UUID,
                                            reviewed_at TIMESTAMP,
                                            review_note VARCHAR(2000),

                                            created_at TIMESTAMP NOT NULL,
                                            updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_purchase_requests_requester_user_id
    ON purchase.purchase_requests(requester_user_id);

CREATE INDEX idx_purchase_requests_status
    ON purchase.purchase_requests(status);

CREATE INDEX idx_purchase_requests_created_at
    ON purchase.purchase_requests(created_at);