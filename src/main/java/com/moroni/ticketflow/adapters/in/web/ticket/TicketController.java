package com.moroni.ticketflow.adapters.in.web.ticket;

import com.moroni.ticketflow.adapters.in.web.common.response.PageResponse;
import com.moroni.ticketflow.adapters.in.web.ticket.request.CreateTicketRequest;
import com.moroni.ticketflow.adapters.in.web.ticket.request.UpdateTicketPriorityRequest;
import com.moroni.ticketflow.adapters.in.web.ticket.request.UpdateTicketStatusRequest;
import com.moroni.ticketflow.adapters.in.web.ticket.response.TicketResponse;
import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketDetails;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.AssignTicketInputPort;
import com.moroni.ticketflow.application.ports.in.CreateTicketInputPort;
import com.moroni.ticketflow.application.ports.in.FindTicketByIdInputPort;
import com.moroni.ticketflow.application.ports.in.SearchTicketsInputPort;
import com.moroni.ticketflow.application.ports.in.TicketDetailsInputPort;
import com.moroni.ticketflow.application.ports.in.UpdateTicketPriorityInputPort;
import com.moroni.ticketflow.application.ports.in.UpdateTicketStatusInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Operations related to IT support tickets")
public class TicketController {

    private final CreateTicketInputPort createTicketInputPort;
    private final FindTicketByIdInputPort findTicketByIdInputPort;
    private final UpdateTicketStatusInputPort updateTicketStatusInputPort;
    private final UpdateTicketPriorityInputPort updateTicketPriorityInputPort;
    private final AssignTicketInputPort assignTicketInputPort;
    private final SearchTicketsInputPort searchTicketsInputPort;
    private final TicketDetailsInputPort ticketDetailsInputPort;

    public TicketController(
            CreateTicketInputPort createTicketInputPort,
            FindTicketByIdInputPort findTicketByIdInputPort,
            UpdateTicketStatusInputPort updateTicketStatusInputPort,
            UpdateTicketPriorityInputPort updateTicketPriorityInputPort,
            AssignTicketInputPort assignTicketInputPort,
            SearchTicketsInputPort searchTicketsInputPort,
            TicketDetailsInputPort ticketDetailsInputPort
    ) {
        this.createTicketInputPort = createTicketInputPort;
        this.findTicketByIdInputPort = findTicketByIdInputPort;
        this.updateTicketStatusInputPort = updateTicketStatusInputPort;
        this.updateTicketPriorityInputPort = updateTicketPriorityInputPort;
        this.assignTicketInputPort = assignTicketInputPort;
        this.searchTicketsInputPort = searchTicketsInputPort;
        this.ticketDetailsInputPort = ticketDetailsInputPort;
    }

    @Operation(summary = "Create a new ticket", description = "Creates a new IT support ticket using the authenticated user as the creator.")
    @PostMapping
    public ResponseEntity<TicketResponse> create(
            @RequestBody @Valid CreateTicketRequest request,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();

        Ticket ticket = createTicketInputPort.create(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                authenticatedUserId,
                request.getCategoryId(),
                request.getSupportQueueId()
        );

        TicketDetails ticketDetails = ticketDetailsInputPort.enrich(ticket);

        TicketResponse response = TicketResponse.fromDomain(ticketDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Search tickets", description = "Search tickets using optional filters with pagination. USER can only see own tickets. ADMIN and TECHNICIAN can see all tickets.")
    @GetMapping
    public ResponseEntity<PageResponse<TicketResponse>> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) UUID supportQueueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        PageResult<Ticket> tickets = searchTicketsInputPort.search(
                status,
                priority,
                supportQueueId,
                page,
                size,
                authenticatedUserId,
                authenticatedUserRole
        );

        PageResult<TicketDetails> ticketDetailsPage = ticketDetailsInputPort.enrichPage(tickets);

        PageResponse<TicketResponse> response = PageResponse.fromPageResult(
                ticketDetailsPage,
                TicketResponse::fromDomain
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find ticket by ID", description = "Returns a ticket by its unique identifier. USER can only access own tickets.")
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> findById(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        Ticket ticket = findTicketByIdInputPort.findById(
                id,
                authenticatedUserId,
                authenticatedUserRole
        );

        TicketDetails ticketDetails = ticketDetailsInputPort.enrich(ticket);

        TicketResponse response = TicketResponse.fromDomain(ticketDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update ticket status", description = "Updates the status of a ticket. ADMIN can update any ticket. TECHNICIAN can update only assigned tickets.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTicketStatusRequest request,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        Ticket ticket = updateTicketStatusInputPort.updateStatus(
                id,
                request.getStatus(),
                authenticatedUserId,
                authenticatedUserRole
        );

        TicketDetails ticketDetails = ticketDetailsInputPort.enrich(ticket);

        TicketResponse response = TicketResponse.fromDomain(ticketDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update ticket priority", description = "Updates the priority of a ticket. ADMIN can update any ticket. TECHNICIAN can update only assigned tickets.")
    @PatchMapping("/{id}/priority")
    public ResponseEntity<TicketResponse> updatePriority(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTicketPriorityRequest request,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        Ticket ticket = updateTicketPriorityInputPort.updatePriority(
                id,
                request.getPriority(),
                authenticatedUserId,
                authenticatedUserRole
        );

        TicketDetails ticketDetails = ticketDetailsInputPort.enrich(ticket);

        TicketResponse response = TicketResponse.fromDomain(ticketDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Assign technician to ticket", description = "Assigns a technician or admin user to a ticket.")
    @PatchMapping("/{id}/assign/{technicianId}")
    public ResponseEntity<TicketResponse> assign(
            @PathVariable UUID id,
            @PathVariable UUID technicianId
    ) {
        Ticket ticket = assignTicketInputPort.assign(
                id,
                technicianId
        );

        TicketDetails ticketDetails = ticketDetailsInputPort.enrich(ticket);

        TicketResponse response = TicketResponse.fromDomain(ticketDetails);

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