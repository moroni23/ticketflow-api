package com.moroni.ticketflow.adapters.in.web.auth.response;

import com.moroni.ticketflow.application.core.domain.LoginResult;

public class LoginResponse {

    private String token;
    private String type;
    private long expiresIn;
    private LoginUserResponse user;

    public LoginResponse(
            String token,
            String type,
            long expiresIn,
            LoginUserResponse user
    ) {
        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public static LoginResponse fromDomain(LoginResult loginResult) {
        return new LoginResponse(
                loginResult.getAuthToken().getToken(),
                loginResult.getAuthToken().getType(),
                loginResult.getAuthToken().getExpiresIn(),
                LoginUserResponse.fromDomain(loginResult.getUser())
        );
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginUserResponse getUser() {
        return user;
    }
}