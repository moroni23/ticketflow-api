package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.ValidateTicketAccessUseCase;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketAccessConfig {

    @Bean
    public ValidateTicketAccessUseCase validateTicketAccessUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        return new ValidateTicketAccessUseCase(ticketRepositoryOutputPort);
    }
}