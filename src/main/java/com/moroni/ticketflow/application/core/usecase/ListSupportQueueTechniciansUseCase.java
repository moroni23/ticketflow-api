package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.SupportQueueTechnician;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.SupportQueueNotFoundException;
import com.moroni.ticketflow.application.ports.in.ListSupportQueueTechniciansInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueTechnicianRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ListSupportQueueTechniciansUseCase implements ListSupportQueueTechniciansInputPort {

    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort;

    public ListSupportQueueTechniciansUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort
    ) {
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.supportQueueTechnicianRepositoryOutputPort = supportQueueTechnicianRepositoryOutputPort;
    }

    @Override
    public List<User> listTechnicians(UUID supportQueueId) {
        if (!supportQueueRepositoryOutputPort.existsById(supportQueueId)) {
            throw new SupportQueueNotFoundException(supportQueueId);
        }

        return supportQueueTechnicianRepositoryOutputPort.findBySupportQueueId(supportQueueId)
                .stream()
                .map(SupportQueueTechnician::getTechnicianId)
                .map(userRepositoryOutputPort::findById)
                .flatMap(java.util.Optional::stream)
                .filter(user -> UserRole.TECHNICIAN.equals(user.getRole())
                        || UserRole.ADMIN.equals(user.getRole()))
                .sorted(Comparator.comparing(User::getName))
                .toList();
    }
}