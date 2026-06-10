package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.AuthToken;
import com.moroni.ticketflow.application.core.domain.LoginResult;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.exception.InvalidCredentialsException;
import com.moroni.ticketflow.application.ports.in.LoginInputPort;
import com.moroni.ticketflow.application.ports.out.PasswordEncoderOutputPort;
import com.moroni.ticketflow.application.ports.out.TokenProviderOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

public class LoginUseCase implements LoginInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final PasswordEncoderOutputPort passwordEncoderOutputPort;
    private final TokenProviderOutputPort tokenProviderOutputPort;

    public LoginUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            PasswordEncoderOutputPort passwordEncoderOutputPort,
            TokenProviderOutputPort tokenProviderOutputPort
    ) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.passwordEncoderOutputPort = passwordEncoderOutputPort;
        this.tokenProviderOutputPort = tokenProviderOutputPort;
    }

    @Override
    public LoginResult login(String email, String password) {
        User user = userRepositoryOutputPort.findByEmail(email.trim().toLowerCase())
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordEncoderOutputPort.matches(
                password,
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        AuthToken authToken = tokenProviderOutputPort.generateToken(user);

        return new LoginResult(user, authToken);
    }
}