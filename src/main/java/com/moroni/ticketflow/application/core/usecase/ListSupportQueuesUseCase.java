package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.SupportQueue;
import com.moroni.ticketflow.application.ports.in.ListSupportQueuesInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;

import java.util.List;

public class ListSupportQueuesUseCase implements ListSupportQueuesInputPort {

    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;

    public ListSupportQueuesUseCase(SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort) {
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
    }

    @Override
    public List<SupportQueue> findAll() {
        return supportQueueRepositoryOutputPort.findAll();
    }
}