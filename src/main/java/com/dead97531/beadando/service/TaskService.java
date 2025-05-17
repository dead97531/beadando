package com.dead97531.beadando.service;

import com.dead97531.beadando.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new HashMap<>();
    private long nextId = 1;

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task create(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    public Optional<Task> update(Long id, Task updated) {
        Task existing = tasks.get(id);
        if (existing == null) return Optional.empty();

        existing.setDescription(updated.getDescription());
        existing.setDueDate(updated.getDueDate());
        existing.setUserId(updated.getUserId());
        return Optional.of(existing);
    }

    public boolean delete(Long id) {
        return tasks.remove(id) != null;
    }
}
