package com.dead97531.beadando.controller;

import com.dead97531.beadando.model.Ticket;
import com.dead97531.beadando.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTickets_ShouldReturnList() throws Exception {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketService.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTicketById_WhenNotFound_ShouldReturn404() throws Exception {
        when(ticketService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        when(ticketService.create(Mockito.any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated());
    }

    @Test
    void sellTicket_WhenExists_ShouldReturnUpdatedTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setSold(true);
        when(ticketService.sellTicket(1L)).thenReturn(Optional.of(ticket));

        mockMvc.perform(put("/api/tickets/1/sell"))
                .andExpect(status().isOk());
    }

    @Test
    void sellTicket_WhenNotFound_ShouldReturn404() throws Exception {
        when(ticketService.sellTicket(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/tickets/1/sell"))
                .andExpect(status().isNotFound());
    }
    @Test
    void getTicketsByEvent_ShouldReturnTicketsForEvent() throws Exception {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        when(ticketService.findByEventId(1L)).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/api/tickets/event/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateTicket_WhenFound_ShouldReturnUpdatedTicket() throws Exception {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(1L);
        updatedTicket.setSold(true);

        when(ticketService.update(Mockito.eq(1L), any(Ticket.class)))
                .thenReturn(Optional.of(updatedTicket));

        mockMvc.perform(put("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTicket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sold").value(true));
    }

    @Test
    void updateTicket_WhenNotFound_ShouldReturn404() throws Exception {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setSold(true);

        when(ticketService.update(Mockito.eq(1L), any(Ticket.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTicket)))
                .andExpect(status().isNotFound());
    }
    @Test
    void deleteTicket_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(ticketService).delete(1L);
    }

}
