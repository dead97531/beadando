package com.dead97531.beadando.repository;

import com.dead97531.beadando.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventId(Long eventId);
}