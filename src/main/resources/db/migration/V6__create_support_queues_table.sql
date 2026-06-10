CREATE TABLE support_queues (
                                id UUID PRIMARY KEY,
                                name VARCHAR(100) NOT NULL UNIQUE,
                                description TEXT,
                                active BOOLEAN NOT NULL,
                                created_at TIMESTAMP NOT NULL
);