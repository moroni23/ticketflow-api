package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.ListTicketHistoryUseCase;
import com.moroni.ticketflow.application.core.usecase.ValidateTicketAccessUseCase;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketHistoryConfig {

    @Bean
    public ListTicketHistoryUseCase listTicketHistoryUseCase(
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        return new ListTicketHistoryUseCase(
                ticketHistoryRepositoryOutputPort,
                validateTicketAccessUseCase
        );
    }
}