package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.AddTicketCommentUseCase;
import com.moroni.ticketflow.application.core.usecase.ListTicketCommentsUseCase;
import com.moroni.ticketflow.application.core.usecase.ValidateTicketAccessUseCase;
import com.moroni.ticketflow.application.ports.out.TicketCommentRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketCommentConfig {

    @Bean
    public AddTicketCommentUseCase addTicketCommentUseCase(
            TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        return new AddTicketCommentUseCase(
                ticketCommentRepositoryOutputPort,
                userRepositoryOutputPort,
                ticketHistoryRepositoryOutputPort,
                validateTicketAccessUseCase
        );
    }

    @Bean
    public ListTicketCommentsUseCase listTicketCommentsUseCase(
            TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        return new ListTicketCommentsUseCase(
                ticketCommentRepositoryOutputPort,
                validateTicketAccessUseCase
        );
    }
}