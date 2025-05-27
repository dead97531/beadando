package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Event;
import com.dead97531.beadando.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> update(Long id, Event updated) {
        return eventRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDate(updated.getDate());
            existing.setLocation(updated.getLocation());
            existing.setCapacity(updated.getCapacity());
            return eventRepository.save(existing);
        });
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
    public void deleteAll() {
        eventRepository.deleteAll();
    }

}
