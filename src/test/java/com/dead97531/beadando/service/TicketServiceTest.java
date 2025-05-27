package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Ticket;
import com.dead97531.beadando.repository.*;
import com.dead97531.beadando.model.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void findAll_ShouldReturnAllTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findAll())
                .thenReturn(Arrays.asList(ticket1, ticket2));

        List<Ticket> result = ticketService.findAll();

        assertEquals(2, result.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void findById_WhenTicketExists_ShouldReturnTicket() {
        Ticket ticket = new Ticket();
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Optional<Ticket> result = ticketService.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void findById_WhenTicketNotExists_ShouldReturnEmpty() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Ticket> result = ticketService.findById(1L);

        assertTrue(result.isEmpty());
    }
    @Test
    void update_WhenTicketExists_ShouldUpdateAndReturnTicket() {
        Ticket existing = new Ticket();
        existing.setId(1L);
        existing.setSold(false);

        Ticket updated = new Ticket();
        updated.setSold(true);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existing);

        Optional<Ticket> result = ticketService.update(1L, updated);

        assertTrue(result.isPresent());
        assertTrue(result.get().isSold());
        verify(ticketRepository).save(existing);
    }
    @Test
    void update_WhenTicketNotExists_ShouldReturnEmptyOptional() {
        Ticket updated = new Ticket();
        updated.setSold(true);

        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Ticket> result = ticketService.update(1L, updated);

        assertTrue(result.isEmpty());
    }
    @Test
    void create_ShouldSaveTicket() {
        Event event = new Event();
        event.setId(1L);
        event.setCapacity(3);

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setSold(false);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketRepository.countByEventId(1L)).thenReturn(0L);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.create(ticket);

        assertNotNull(result);
        verify(ticketRepository).save(ticket);
    }

    @Test
    void sellTicket_WhenTicketExists_ShouldMarkAsSold() {
        Ticket ticket = new Ticket();
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Optional<Ticket> result = ticketService.sellTicket(1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().isSold());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void sellTicket_WhenTicketNotExists_ShouldReturnEmpty() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Ticket> result = ticketService.sellTicket(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByEventId_ShouldReturnTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findByEventId(1L))
                .thenReturn(Arrays.asList(ticket1, ticket2));

        List<Ticket> result = ticketService.findByEventId(1L);

        assertEquals(2, result.size());
    }
    @Test
    void create_WhenEventCapacityReached_ShouldThrowException() {
        Event event = new Event();
        event.setId(1L);
        event.setCapacity(1);

        Ticket ticket = new Ticket();
        ticket.setEvent(event);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketRepository.countByEventId(1L)).thenReturn(1L);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ticketService.create(ticket);
        });

        assertEquals("Event capacity reached. No more tickets can be sold.", exception.getMessage());
    }
    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        ticketService.delete(1L);
        verify(ticketRepository).deleteById(1L);
    }

}