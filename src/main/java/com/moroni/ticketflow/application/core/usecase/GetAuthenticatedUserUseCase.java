package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.GetAuthenticatedUserInputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class GetAuthenticatedUserUseCase implements GetAuthenticatedUserInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;

    public GetAuthenticatedUserUseCase(UserRepositoryOutputPort userRepositoryOutputPort) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
    }

    @Override
    public User getAuthenticatedUser(UUID userId) {
        return userRepositoryOutputPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
