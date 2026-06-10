CREATE TABLE ticket_comments (
                                 id UUID PRIMARY KEY,
                                 ticket_id UUID NOT NULL,
                                 author_user_id UUID NOT NULL,
                                 message TEXT NOT NULL,
                                 created_at TIMESTAMP NOT NULL,

                                 CONSTRAINT fk_ticket_comments_ticket
                                     FOREIGN KEY (ticket_id)
                                         REFERENCES tickets(id),

                                 CONSTRAINT fk_ticket_comments_author_user
                                     FOREIGN KEY (author_user_id)
                                         REFERENCES users(id)
);