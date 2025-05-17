package com.dead97531.beadando.repository;

import com.dead97531.beadando.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {}