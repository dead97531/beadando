package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Ticket;
import com.dead97531.beadando.model.Event;
import com.dead97531.beadando.repository.TicketRepository;
import com.dead97531.beadando.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket create(Ticket ticket) {
        Long eventId = ticket.getEvent().getId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        long soldTickets = ticketRepository.countByEventId(eventId);
        if (soldTickets >= event.getCapacity()) {
            throw new IllegalStateException(
                    "Event capacity reached. No more tickets can be sold."
            );
        }

        return ticketRepository.save(ticket);
    }

    public List<Ticket> findByEventId(Long eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    public Optional<Ticket> update(Long id, Ticket updated) {
        return ticketRepository.findById(id).map(existing -> {
            existing.setSold(updated.isSold());
            return ticketRepository.save(existing);
        });
    }
    public Optional<Ticket> sellTicket(Long id) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setSold(true);
            return ticketRepository.save(ticket);
        });
    }
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
