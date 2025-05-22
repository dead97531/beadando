package com.dead97531.beadando.controller;

import com.dead97531.beadando.model.Ticket;
import com.dead97531.beadando.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("/event/{eventId}")
    public List<Ticket> getTicketsByEvent(@PathVariable("eventId")
                                              Long eventId) {
        return service.findByEventId(eventId);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = service.create(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<Ticket> sellTicket(@PathVariable("id") Long id) {
        return service.sellTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
