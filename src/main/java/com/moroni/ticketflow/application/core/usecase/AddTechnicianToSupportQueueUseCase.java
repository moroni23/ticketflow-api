package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.SupportQueueTechnician;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTechnicianException;
import com.moroni.ticketflow.application.core.exception.SupportQueueNotFoundException;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.AddTechnicianToSupportQueueInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueTechnicianRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class AddTechnicianToSupportQueueUseCase implements AddTechnicianToSupportQueueInputPort {

    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort;

    public AddTechnicianToSupportQueueUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort
    ) {
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.supportQueueTechnicianRepositoryOutputPort = supportQueueTechnicianRepositoryOutputPort;
    }

    @Override
    public User addTechnician(
            UUID supportQueueId,
            UUID technicianId
    ) {
        if (!supportQueueRepositoryOutputPort.existsById(supportQueueId)) {
            throw new SupportQueueNotFoundException(supportQueueId);
        }

        User technician = userRepositoryOutputPort.findById(technicianId)
                .orElseThrow(() -> new UserNotFoundException(technicianId));

        if (!UserRole.TECHNICIAN.equals(technician.getRole())
                && !UserRole.ADMIN.equals(technician.getRole())) {
            throw new InvalidTechnicianException(technicianId);
        }

        boolean alreadyLinked = supportQueueTechnicianRepositoryOutputPort
                .existsBySupportQueueIdAndTechnicianId(
                        supportQueueId,
                        technicianId
                );

        if (!alreadyLinked) {
            SupportQueueTechnician supportQueueTechnician = SupportQueueTechnician.create(
                    supportQueueId,
                    technicianId
            );

            supportQueueTechnicianRepositoryOutputPort.save(supportQueueTechnician);
        }

        return technician;
    }
}