package com.dead97531.beadando.repository;

import com.dead97531.beadando.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {}