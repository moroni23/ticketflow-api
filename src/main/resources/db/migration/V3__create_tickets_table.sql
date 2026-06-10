CREATE TABLE tickets (
                         id UUID PRIMARY KEY,
                         title VARCHAR(150) NOT NULL,
                         description TEXT NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         priority VARCHAR(50) NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL,
                         closed_at TIMESTAMP,
                         created_by_user_id UUID NOT NULL,
                         assigned_to_user_id UUID,
                         category_id UUID NOT NULL,

                         CONSTRAINT fk_tickets_created_by_user
                             FOREIGN KEY (created_by_user_id)
                                 REFERENCES users(id),

                         CONSTRAINT fk_tickets_assigned_to_user
                             FOREIGN KEY (assigned_to_user_id)
                                 REFERENCES users(id),

                         CONSTRAINT fk_tickets_category
                             FOREIGN KEY (category_id)
                                 REFERENCES categories(id)
);