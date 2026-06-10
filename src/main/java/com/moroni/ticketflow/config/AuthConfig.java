package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.LoginUseCase;
import com.moroni.ticketflow.application.ports.out.PasswordEncoderOutputPort;
import com.moroni.ticketflow.application.ports.out.TokenProviderOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.moroni.ticketflow.application.core.usecase.GetAuthenticatedUserUseCase;

@Configuration
public class AuthConfig {

    @Bean
    public LoginUseCase loginUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            PasswordEncoderOutputPort passwordEncoderOutputPort,
            TokenProviderOutputPort tokenProviderOutputPort
    ) {
        return new LoginUseCase(
                userRepositoryOutputPort,
                passwordEncoderOutputPort,
                tokenProviderOutputPort
        );
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort
    ) {
        return new GetAuthenticatedUserUseCase(userRepositoryOutputPort);
    }
}