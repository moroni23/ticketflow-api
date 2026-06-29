package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.AddTechnicianToSupportQueueUseCase;
import com.moroni.ticketflow.application.core.usecase.CreateSupportQueueUseCase;
import com.moroni.ticketflow.application.core.usecase.ListSupportQueueTechniciansUseCase;
import com.moroni.ticketflow.application.core.usecase.ListSupportQueuesUseCase;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueTechnicianRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;
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

    @Bean
    public AddTechnicianToSupportQueueUseCase addTechnicianToSupportQueueUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort
    ) {
        return new AddTechnicianToSupportQueueUseCase(
                supportQueueRepositoryOutputPort,
                userRepositoryOutputPort,
                supportQueueTechnicianRepositoryOutputPort
        );
    }

    @Bean
    public ListSupportQueueTechniciansUseCase listSupportQueueTechniciansUseCase(
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort
    ) {
        return new ListSupportQueueTechniciansUseCase(
                supportQueueRepositoryOutputPort,
                userRepositoryOutputPort,
                supportQueueTechnicianRepositoryOutputPort
        );
    }
}