package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.CreateUserUseCase;
import com.moroni.ticketflow.application.core.usecase.ListTechniciansUseCase;
import com.moroni.ticketflow.application.ports.out.PasswordEncoderOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            PasswordEncoderOutputPort passwordEncoderOutputPort
    ) {
        return new CreateUserUseCase(
                userRepositoryOutputPort,
                passwordEncoderOutputPort
        );
    }

    @Bean
    public ListTechniciansUseCase listTechniciansUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort
    ) {
        return new ListTechniciansUseCase(userRepositoryOutputPort);
    }
}