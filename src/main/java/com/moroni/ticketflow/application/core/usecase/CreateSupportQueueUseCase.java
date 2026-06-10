package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.SupportQueue;
import com.moroni.ticketflow.application.core.exception.SupportQueueAlreadyExistsException;
import com.moroni.ticketflow.application.ports.in.CreateSupportQueueInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;

public class CreateSupportQueueUseCase implements CreateSupportQueueInputPort {

    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;

    public CreateSupportQueueUseCase(SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort) {
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
    }

    @Override
    public SupportQueue create(String name, String description) {
        String normalizedName = name.trim();

        if (supportQueueRepositoryOutputPort.existsByName(normalizedName)) {
            throw new SupportQueueAlreadyExistsException(normalizedName);
        }

        SupportQueue supportQueue = SupportQueue.create(
                normalizedName,
                description
        );

        return supportQueueRepositoryOutputPort.save(supportQueue);
    }
}