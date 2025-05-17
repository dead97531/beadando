package com.dead97531.beadando.model;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private Long userId;

    public Task() {}

    public Task(Long id, String description, LocalDate dueDate, Long userId) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
