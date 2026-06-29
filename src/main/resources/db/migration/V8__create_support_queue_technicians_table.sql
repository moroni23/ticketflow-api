CREATE TABLE support_queue_technicians (
                                           id UUID PRIMARY KEY,
                                           support_queue_id UUID NOT NULL,
                                           technician_id UUID NOT NULL,
                                           created_at TIMESTAMP NOT NULL,

                                           CONSTRAINT fk_support_queue_technicians_queue
                                               FOREIGN KEY (support_queue_id)
                                                   REFERENCES support_queues(id),

                                           CONSTRAINT fk_support_queue_technicians_user
                                               FOREIGN KEY (technician_id)
                                                   REFERENCES users(id),

                                           CONSTRAINT uk_support_queue_technician
                                               UNIQUE (support_queue_id, technician_id)
);