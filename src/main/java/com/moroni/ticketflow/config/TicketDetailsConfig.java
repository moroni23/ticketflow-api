package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.TicketDetailsUseCase;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketDetailsConfig {

    @Bean
    public TicketDetailsUseCase ticketDetailsUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            CategoryRepositoryOutputPort categoryRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort
    ) {
        return new TicketDetailsUseCase(
                userRepositoryOutputPort,
                categoryRepositoryOutputPort,
                supportQueueRepositoryOutputPort
        );
    }
}