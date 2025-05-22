package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Event;
import com.dead97531.beadando.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void findAll_ShouldReturnAllEvents() {
        Event event1 = new Event();
        Event event2 = new Event();
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<Event> result = eventService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_WhenEventExists_ShouldReturnEvent() {
        Event event = new Event();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.findById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void findById_WhenEventNotExists_ShouldReturnEmpty() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void create_ShouldSaveEvent() {
        Event event = new Event();
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.create(event);

        assertNotNull(result);
        verify(eventRepository).save(event);
    }

    @Test
    void update_WhenEventExists_ShouldUpdateEvent() {
        Event existingEvent = new Event();
        existingEvent.setId(1L);
        existingEvent.setName("Old Name");

        Event updatedEvent = new Event();
        updatedEvent.setName("New Name");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(existingEvent);

        Optional<Event> result = eventService.update(1L, updatedEvent);

        assertTrue(result.isPresent());
        assertEquals("New Name", result.get().getName());
    }

    @Test
    void update_WhenEventNotExists_ShouldReturnEmpty() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.update(1L, new Event());

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_ShouldCallRepository() {
        eventService.delete(1L);
        verify(eventRepository).deleteById(1L);
    }
}