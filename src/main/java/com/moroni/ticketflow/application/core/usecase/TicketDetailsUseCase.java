package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Category;
import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.SupportQueue;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketDetails;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.ports.in.TicketDetailsInputPort;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class TicketDetailsUseCase implements TicketDetailsInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final CategoryRepositoryOutputPort categoryRepositoryOutputPort;
    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;

    public TicketDetailsUseCase(
            UserRepositoryOutputPort userRepositoryOutputPort,
            CategoryRepositoryOutputPort categoryRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort
    ) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.categoryRepositoryOutputPort = categoryRepositoryOutputPort;
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
    }

    @Override
    public TicketDetails enrich(Ticket ticket) {
        String createdByUserName = findUserName(ticket.getCreatedByUserId());
        String assignedToUserName = findUserName(ticket.getAssignedToUserId());
        String categoryName = findCategoryName(ticket.getCategoryId());
        String supportQueueName = findSupportQueueName(ticket.getSupportQueueId());

        return new TicketDetails(
                ticket,
                createdByUserName,
                assignedToUserName,
                categoryName,
                supportQueueName
        );
    }

    @Override
    public PageResult<TicketDetails> enrichPage(PageResult<Ticket> ticketPage) {
        return new PageResult<>(
                ticketPage.getContent()
                        .stream()
                        .map(this::enrich)
                        .toList(),
                ticketPage.getPage(),
                ticketPage.getSize(),
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.isFirst(),
                ticketPage.isLast()
        );
    }

    private String findUserName(UUID userId) {
        if (userId == null) {
            return null;
        }

        return userRepositoryOutputPort.findById(userId)
                .map(User::getName)
                .orElse(null);
    }

    private String findCategoryName(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }

        return categoryRepositoryOutputPort.findById(categoryId)
                .map(Category::getName)
                .orElse(null);
    }

    private String findSupportQueueName(UUID supportQueueId) {
        if (supportQueueId == null) {
            return null;
        }

        return supportQueueRepositoryOutputPort.findById(supportQueueId)
                .map(SupportQueue::getName)
                .orElse(null);
    }
}