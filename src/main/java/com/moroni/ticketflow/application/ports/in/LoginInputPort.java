package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.LoginResult;

public interface LoginInputPort {

    LoginResult login(String email, String password);
}
