package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.User;

public interface CreateUserInputPort {

    User create(String name, String email, String password, String role);
}
