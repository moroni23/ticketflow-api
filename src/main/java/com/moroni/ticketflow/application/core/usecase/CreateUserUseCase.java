package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.UserAlreadyExistsException;
import com.moroni.ticketflow.application.ports.in.CreateUserInputPort;
import com.moroni.ticketflow.application.ports.out.PasswordEncoderOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

public class CreateUserUseCase implements CreateUserInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final PasswordEncoderOutputPort passwordEncoderOutputPort;

    public CreateUserUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            PasswordEncoderOutputPort passwordEncoderOutputPort
    ) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.passwordEncoderOutputPort = passwordEncoderOutputPort;
    }

    @Override
    public User create(String name, String email, String password, String role) {
        if (userRepositoryOutputPort.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        UserRole userRole = UserRole.valueOf(role.trim().toUpperCase());

        String encodedPassword = passwordEncoderOutputPort.encode(password);

        User user = User.create(
                name,
                email,
                encodedPassword,
                userRole
        );

        return userRepositoryOutputPort.save(user);
    }
}
