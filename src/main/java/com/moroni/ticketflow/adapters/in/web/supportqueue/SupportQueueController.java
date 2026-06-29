package com.moroni.ticketflow.adapters.in.web.supportqueue;

import com.moroni.ticketflow.adapters.in.web.supportqueue.request.CreateSupportQueueRequest;
import com.moroni.ticketflow.adapters.in.web.supportqueue.response.SupportQueueResponse;
import com.moroni.ticketflow.adapters.in.web.user.response.TechnicianResponse;
import com.moroni.ticketflow.application.core.domain.SupportQueue;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.ports.in.AddTechnicianToSupportQueueInputPort;
import com.moroni.ticketflow.application.ports.in.CreateSupportQueueInputPort;
import com.moroni.ticketflow.application.ports.in.ListSupportQueueTechniciansInputPort;
import com.moroni.ticketflow.application.ports.in.ListSupportQueuesInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/support-queues")
@Tag(name = "Support Queues", description = "Operations related to support queues")
public class SupportQueueController {

    private final CreateSupportQueueInputPort createSupportQueueInputPort;
    private final ListSupportQueuesInputPort listSupportQueuesInputPort;
    private final AddTechnicianToSupportQueueInputPort addTechnicianToSupportQueueInputPort;
    private final ListSupportQueueTechniciansInputPort listSupportQueueTechniciansInputPort;

    public SupportQueueController(
            CreateSupportQueueInputPort createSupportQueueInputPort,
            ListSupportQueuesInputPort listSupportQueuesInputPort,
            AddTechnicianToSupportQueueInputPort addTechnicianToSupportQueueInputPort,
            ListSupportQueueTechniciansInputPort listSupportQueueTechniciansInputPort
    ) {
        this.createSupportQueueInputPort = createSupportQueueInputPort;
        this.listSupportQueuesInputPort = listSupportQueuesInputPort;
        this.addTechnicianToSupportQueueInputPort = addTechnicianToSupportQueueInputPort;
        this.listSupportQueueTechniciansInputPort = listSupportQueueTechniciansInputPort;
    }

    @Operation(summary = "Create support queue", description = "Creates a new support queue.")
    @PostMapping
    public ResponseEntity<SupportQueueResponse> create(
            @RequestBody @Valid CreateSupportQueueRequest request
    ) {
        SupportQueue supportQueue = createSupportQueueInputPort.create(
                request.getName(),
                request.getDescription()
        );

        SupportQueueResponse response = SupportQueueResponse.fromDomain(supportQueue);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List support queues", description = "Lists all support queues.")
    @GetMapping
    public ResponseEntity<List<SupportQueueResponse>> findAll() {
        List<SupportQueueResponse> response = listSupportQueuesInputPort.findAll()
                .stream()
                .map(SupportQueueResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add technician to support queue", description = "Links a technician to a support queue.")
    @PostMapping("/{queueId}/technicians/{technicianId}")
    public ResponseEntity<TechnicianResponse> addTechnician(
            @PathVariable UUID queueId,
            @PathVariable UUID technicianId
    ) {
        User technician = addTechnicianToSupportQueueInputPort.addTechnician(
                queueId,
                technicianId
        );

        TechnicianResponse response = TechnicianResponse.fromDomain(technician);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List support queue technicians", description = "Lists technicians linked to a support queue.")
    @GetMapping("/{queueId}/technicians")
    public ResponseEntity<List<TechnicianResponse>> listTechnicians(
            @PathVariable UUID queueId
    ) {
        List<TechnicianResponse> response = listSupportQueueTechniciansInputPort.listTechnicians(queueId)
                .stream()
                .map(TechnicianResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }
}