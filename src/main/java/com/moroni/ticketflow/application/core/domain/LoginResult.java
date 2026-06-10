package com.moroni.ticketflow.application.core.domain;

public class LoginResult {

    private User user;
    private AuthToken authToken;

    public LoginResult(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}