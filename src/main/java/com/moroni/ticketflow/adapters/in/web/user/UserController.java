package com.moroni.ticketflow.adapters.in.web.user;

import com.moroni.ticketflow.adapters.in.web.user.request.CreateUserRequest;
import com.moroni.ticketflow.adapters.in.web.user.response.TechnicianResponse;
import com.moroni.ticketflow.adapters.in.web.user.response.UserResponse;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.ports.in.CreateUserInputPort;
import com.moroni.ticketflow.application.ports.in.ListTechniciansInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to users")
public class UserController {

    private final CreateUserInputPort createUserInputPort;
    private final ListTechniciansInputPort listTechniciansInputPort;

    public UserController(
            CreateUserInputPort createUserInputPort,
            ListTechniciansInputPort listTechniciansInputPort
    ) {
        this.createUserInputPort = createUserInputPort;
        this.listTechniciansInputPort = listTechniciansInputPort;
    }

    @Operation(summary = "Create user", description = "Creates a new user.")
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid CreateUserRequest request
    ) {
        User user = createUserInputPort.create(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        UserResponse response = UserResponse.fromDomain(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List technicians", description = "Lists users that can be assigned to tickets.")
    @GetMapping("/technicians")
    public ResponseEntity<List<TechnicianResponse>> listTechnicians() {
        List<TechnicianResponse> response = listTechniciansInputPort.listTechnicians()
                .stream()
                .map(TechnicianResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }
}