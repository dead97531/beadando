package com.dead97531.beadando.service;

import com.dead97531.beadando.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User create(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> update(Long id, User updated) {
        User existing = users.get(id);
        if (existing == null) return Optional.empty();

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return Optional.of(existing);
    }

    public boolean delete(Long id) {
        return users.remove(id) != null;
    }
}
