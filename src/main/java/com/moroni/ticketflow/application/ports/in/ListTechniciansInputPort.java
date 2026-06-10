package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.User;

import java.util.List;

public interface ListTechniciansInputPort {

    List<User> listTechnicians();
}