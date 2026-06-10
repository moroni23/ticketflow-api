package com.moroni.ticketflow.adapters.in.web.ticket.history;

import com.moroni.ticketflow.adapters.in.web.ticket.history.response.TicketHistoryResponse;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.ListTicketHistoryInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets/{ticketId}/history")
@Tag(name = "Ticket History", description = "Operations related to ticket event history")
public class TicketHistoryController {

    private final ListTicketHistoryInputPort listTicketHistoryInputPort;

    public TicketHistoryController(ListTicketHistoryInputPort listTicketHistoryInputPort) {
        this.listTicketHistoryInputPort = listTicketHistoryInputPort;
    }

    @Operation(summary = "List ticket history", description = "Lists all history events for a ticket in chronological order.")
    @GetMapping
    public ResponseEntity<List<TicketHistoryResponse>> listHistory(
            @PathVariable UUID ticketId,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        List<TicketHistoryResponse> response = listTicketHistoryInputPort.listByTicketId(
                        ticketId,
                        authenticatedUserId,
                        authenticatedUserRole
                )
                .stream()
                .map(TicketHistoryResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }

    private UserRole extractUserRole(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.replace("ROLE_", ""))
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Authenticated user role not found"));
    }
}