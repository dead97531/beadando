package com.dead97531.beadando.controller;

import com.dead97531.beadando.model.Event;
import com.dead97531.beadando.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private Event createEvent(Long id, String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        return event;
    }

    @Test
    void getAllEvents_ShouldReturnListOfEvents() throws Exception {
        Event event1 = createEvent(1L, "Koncert");
        Event event2 = createEvent(2L, "Színház");
        List<Event> events = Arrays.asList(event1, event2);

        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(event1.getId()))
                .andExpect(jsonPath("$[1].id").value(event2.getId()));
    }

    @Test
    void getEventById_WhenFound_ShouldReturnEvent() throws Exception {
        Event event = createEvent(1L, "Koncert");
        when(eventService.findById(1L)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/api/events/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.name").value(event.getName()));
    }

    @Test
    void getEventById_WhenNotFound_ShouldReturn404() throws Exception {
        when(eventService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/events/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEvent_ShouldReturnCreatedEvent() throws Exception {
        Event eventToCreate = createEvent(null, "Új esemény");
        Event createdEvent = createEvent(1L, "Új esemény");

        when(eventService.create(any(Event.class))).thenReturn(createdEvent);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdEvent.getId()))
                .andExpect(jsonPath("$.name").value(createdEvent.getName()));
    }

    @Test
    void updateEvent_WhenFound_ShouldReturnUpdatedEvent() throws Exception {
        Event updatedEvent = createEvent(1L, "Frissített esemény");

        when(eventService.update(Mockito.eq(1L), any(Event.class))).thenReturn(Optional.of(updatedEvent));

        mockMvc.perform(put("/api/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedEvent.getId()))
                .andExpect(jsonPath("$.name").value(updatedEvent.getName()));
    }

    @Test
    void updateEvent_WhenNotFound_ShouldReturn404() throws Exception {
        Event updatedEvent = createEvent(null, "Nem létező esemény");

        when(eventService.update(Mockito.eq(1L), any(Event.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEvent)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEvent_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/events/{id}", 1L))
                .andExpect(status().isNoContent());

        Mockito.verify(eventService).delete(1L);
    }
}
