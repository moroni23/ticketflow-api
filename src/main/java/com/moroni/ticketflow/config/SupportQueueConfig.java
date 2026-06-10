package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.CreateSupportQueueUseCase;
import com.moroni.ticketflow.application.core.usecase.ListSupportQueuesUseCase;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupportQueueConfig {

    @Bean
    public CreateSupportQueueUseCase createSupportQueueUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort
    ) {
        return new CreateSupportQueueUseCase(supportQueueRepositoryOutputPort);
    }

    @Bean
    public ListSupportQueuesUseCase listSupportQueuesUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort
    ) {
        return new ListSupportQueuesUseCase(supportQueueRepositoryOutputPort);
    }
}