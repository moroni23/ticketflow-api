package com.moroni.ticketflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TicketFlow API")
                        .description("REST API for IT ticket management using Java, Spring Boot, PostgreSQL and Hexagonal Architecture.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Moroni Pereira")
                                .email("moronispereira@gmail.com")
                        )
                );
    }
}