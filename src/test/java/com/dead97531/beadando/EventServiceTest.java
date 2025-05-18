package com.dead97531.beadando;

import com.dead97531.beadando.model.Event;
import com.dead97531.beadando.repository.EventRepository;
import com.dead97531.beadando.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllEvents() {
        Event event1 = new Event();
        event1.setName("Concert");
        Event event2 = new Event();
        event2.setName("Theater");
        List<Event> events = Arrays.asList(event1, event2);

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.findAll();

        assertEquals(2, result.size());
        verify(eventRepository).findAll();
    }

    @Test
    void findById_WhenEventExists_ShouldReturnEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Concert");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Concert", result.get().getName());
    }

    @Test
    void create_ShouldSaveAndReturnEvent() {
        Event event = new Event();
        event.setName("New Concert");
        event.setDate(LocalDate.now());

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.create(event);

        assertNotNull(result);
        assertEquals("New Concert", result.getName());
        verify(eventRepository).save(event);
    }

    @Test
    void update_WhenEventExists_ShouldUpdateAndReturnEvent() {
        Long id = 1L;
        Event existingEvent = new Event();
        existingEvent.setId(id);
        existingEvent.setName("Old Concert");

        Event updatedEvent = new Event();
        updatedEvent.setName("Updated Concert");

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(existingEvent);

        Optional<Event> result = eventService.update(id, updatedEvent);

        assertTrue(result.isPresent());
        assertEquals("Updated Concert", result.get().getName());
    }

    @Test
    void delete_ShouldCallRepository() {
        Long id = 1L;

        eventService.delete(id);

        verify(eventRepository).deleteById(id);
    }
}
