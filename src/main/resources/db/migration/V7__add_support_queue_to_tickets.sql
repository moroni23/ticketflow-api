ALTER TABLE tickets
    ADD COLUMN support_queue_id UUID;

ALTER TABLE tickets
    ADD CONSTRAINT fk_tickets_support_queue
        FOREIGN KEY (support_queue_id)
            REFERENCES support_queues(id);