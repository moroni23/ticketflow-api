package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.ValidateTicketTechnicianAccessUseCase;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketTechnicianAccessConfig {

    @Bean
    public ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        return new ValidateTicketTechnicianAccessUseCase(ticketRepositoryOutputPort);
    }
}