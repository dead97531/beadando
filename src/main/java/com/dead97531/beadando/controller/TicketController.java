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
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }
    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = service.create(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateEvent(@PathVariable Long id,
                                              @RequestBody Ticket ticket) {
        return service.update(id, ticket)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/event/{eventId}")
    public List<Ticket> getTicketsByEvent(@PathVariable("eventId")
                                              Long eventId) {
        return service.findByEventId(eventId);
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<Ticket> sellTicket(@PathVariable("id") Long id) {
        return service.sellTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
