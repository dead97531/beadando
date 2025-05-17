package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Ticket;
import com.dead97531.beadando.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> update(Long id, Ticket updated) {
        return ticketRepository.findById(id).map(existing -> {
            existing.setPrice(updated.getPrice());
            existing.setSeatNumber(updated.getSeatNumber());
            existing.setSold(updated.isSold());
            return ticketRepository.save(existing);
        });
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}