package com.irctc.service;

import com.irctc.request.BookTicketRequest;
import com.irctc.response.GenericResponse;

public interface TicketService {
    GenericResponse bookTicket(BookTicketRequest bookTicketRequest);
    GenericResponse cancelTicket(String ticketNo);
}
