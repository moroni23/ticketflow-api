package com.moroni.ticketflow.adapters.in.web.ticket.comment;

import com.moroni.ticketflow.adapters.in.web.ticket.comment.request.AddTicketCommentRequest;
import com.moroni.ticketflow.adapters.in.web.ticket.comment.response.TicketCommentHistoryResponse;
import com.moroni.ticketflow.adapters.in.web.ticket.comment.response.TicketCommentResponse;
import com.moroni.ticketflow.application.core.domain.TicketComment;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.AddTicketCommentInputPort;
import com.moroni.ticketflow.application.ports.in.ListTicketCommentsInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
@Tag(name = "Ticket Comments", description = "Operations related to ticket comments")
public class TicketCommentController {

    private final AddTicketCommentInputPort addTicketCommentInputPort;
    private final ListTicketCommentsInputPort listTicketCommentsInputPort;

    public TicketCommentController(
            AddTicketCommentInputPort addTicketCommentInputPort,
            ListTicketCommentsInputPort listTicketCommentsInputPort
    ) {
        this.addTicketCommentInputPort = addTicketCommentInputPort;
        this.listTicketCommentsInputPort = listTicketCommentsInputPort;
    }

    @Operation(summary = "Add comment to ticket", description = "Adds a comment to a ticket using the authenticated user as the author.")
    @PostMapping
    public ResponseEntity<TicketCommentResponse> addComment(
            @PathVariable UUID ticketId,
            @RequestBody @Valid AddTicketCommentRequest request,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        TicketComment ticketComment = addTicketCommentInputPort.addComment(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole,
                request.getMessage()
        );

        TicketCommentResponse response = TicketCommentResponse.fromDomain(ticketComment);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List ticket comments", description = "Lists all comments for a ticket in chronological order.")
    @GetMapping
    public ResponseEntity<List<TicketCommentHistoryResponse>> listComments(
            @PathVariable UUID ticketId,
            Authentication authentication
    ) {
        UUID authenticatedUserId = (UUID) authentication.getPrincipal();
        UserRole authenticatedUserRole = extractUserRole(authentication);

        List<TicketCommentHistoryResponse> response = listTicketCommentsInputPort.listByTicketId(
                        ticketId,
                        authenticatedUserId,
                        authenticatedUserRole
                )
                .stream()
                .map(TicketCommentHistoryResponse::fromDomain)
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