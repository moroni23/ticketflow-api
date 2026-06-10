package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.ListTechniciansInputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.List;

public class ListTechniciansUseCase implements ListTechniciansInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;

    public ListTechniciansUseCase(UserRepositoryOutputPort userRepositoryOutputPort) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
    }

    @Override
    public List<User> listTechnicians() {
        return userRepositoryOutputPort.findByRoles(
                List.of(UserRole.TECHNICIAN, UserRole.ADMIN)
        );
    }
}