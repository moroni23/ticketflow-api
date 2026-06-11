package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.AssignTicketUseCase;
import com.moroni.ticketflow.application.core.usecase.CreateTicketUseCase;
import com.moroni.ticketflow.application.core.usecase.FindTicketByIdUseCase;
import com.moroni.ticketflow.application.core.usecase.SearchTicketsUseCase;
import com.moroni.ticketflow.application.core.usecase.UpdateTicketPriorityUseCase;
import com.moroni.ticketflow.application.core.usecase.UpdateTicketStatusUseCase;
import com.moroni.ticketflow.application.core.usecase.UpdateTicketSupportQueueUseCase;
import com.moroni.ticketflow.application.core.usecase.ValidateTicketTechnicianAccessUseCase;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {

    @Bean
    public CreateTicketUseCase createTicketUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            CategoryRepositoryOutputPort categoryRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort
    ) {
        return new CreateTicketUseCase(
                ticketRepositoryOutputPort,
                userRepositoryOutputPort,
                categoryRepositoryOutputPort,
                supportQueueRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort
        );
    }

    @Bean
    public FindTicketByIdUseCase findTicketByIdUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        return new FindTicketByIdUseCase(ticketRepositoryOutputPort);
    }

    @Bean
    public SearchTicketsUseCase searchTicketsUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        return new SearchTicketsUseCase(ticketRepositoryOutputPort);
    }

    @Bean
    public UpdateTicketStatusUseCase updateTicketStatusUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        return new UpdateTicketStatusUseCase(
                ticketRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort,
                validateTicketTechnicianAccessUseCase
        );
    }

    @Bean
    public UpdateTicketPriorityUseCase updateTicketPriorityUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        return new UpdateTicketPriorityUseCase(
                ticketRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort,
                validateTicketTechnicianAccessUseCase
        );
    }

    @Bean
    public UpdateTicketSupportQueueUseCase updateTicketSupportQueueUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        return new UpdateTicketSupportQueueUseCase(
                ticketRepositoryOutputPort,
                supportQueueRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort,
                validateTicketTechnicianAccessUseCase
        );
    }

    @Bean
    public AssignTicketUseCase assignTicketUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort
    ) {
        return new AssignTicketUseCase(
                ticketRepositoryOutputPort,
                userRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort
        );
    }
}