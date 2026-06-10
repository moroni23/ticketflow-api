CREATE TABLE ticket_history (
                                id UUID PRIMARY KEY,
                                ticket_id UUID NOT NULL,
                                event_type VARCHAR(50) NOT NULL,
                                description TEXT NOT NULL,
                                created_at TIMESTAMP NOT NULL,

                                CONSTRAINT fk_ticket_history_ticket
                                    FOREIGN KEY (ticket_id)
                                        REFERENCES tickets(id)
);