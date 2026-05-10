package com.irctc.controller;

import com.irctc.request.BookTicketRequest;
import com.irctc.response.GenericResponse;
import com.irctc.service.TicketService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/bookTicket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> bookTicket(@RequestBody BookTicketRequest bookTicketRequest) {
        return ResponseEntity.ok(ticketService.bookTicket(bookTicketRequest));
    }

    @GetMapping(value = "/cancelTicket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> cancelTicket(@RequestParam String ticketNo) {
        return ResponseEntity.ok(ticketService.cancelTicket(ticketNo));
    }
}
