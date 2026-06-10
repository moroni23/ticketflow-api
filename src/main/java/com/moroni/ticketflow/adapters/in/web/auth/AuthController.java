package com.moroni.ticketflow.adapters.in.web.auth;

import com.moroni.ticketflow.adapters.in.web.auth.request.LoginRequest;
import com.moroni.ticketflow.adapters.in.web.auth.response.LoginResponse;
import com.moroni.ticketflow.application.core.domain.LoginResult;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.ports.in.LoginInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moroni.ticketflow.adapters.in.web.auth.response.AuthenticatedUserResponse;
import com.moroni.ticketflow.application.ports.in.GetAuthenticatedUserInputPort;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication")
public class AuthController {

    private final LoginInputPort loginInputPort;
    private final GetAuthenticatedUserInputPort getAuthenticatedUserInputPort;

    public AuthController(
            LoginInputPort loginInputPort,
            GetAuthenticatedUserInputPort getAuthenticatedUserInputPort
    ) {
        this.loginInputPort = loginInputPort;
        this.getAuthenticatedUserInputPort = getAuthenticatedUserInputPort;
    }
    @Operation(summary = "Login", description = "Authenticates a user using email and password and returns a JWT token.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResult loginResult = loginInputPort.login(
                request.getEmail(),
                request.getPassword()
        );

        LoginResponse response = LoginResponse.fromDomain(loginResult);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get authenticated user", description = "Returns information about the currently authenticated user.")
    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserResponse> getAuthenticatedUser(
            Authentication authentication
    ) {
        UUID userId = (UUID) authentication.getPrincipal();

        User user = getAuthenticatedUserInputPort.getAuthenticatedUser(userId);

        AuthenticatedUserResponse response = AuthenticatedUserResponse.fromDomain(user);

        return ResponseEntity.ok(response);
    }
}
