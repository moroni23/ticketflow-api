package com.moroni.ticketflow.application.core.domain;

public class AuthToken {

    private String token;
    private String type;
    private long expiresIn;

    public AuthToken(String token, String type, long expiresIn) {
        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
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
}
