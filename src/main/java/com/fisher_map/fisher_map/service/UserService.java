package com.fisher_map.fisher_map.service;

import com.fisher_map.fisher_map.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String name);

    User findById(UUID id);

    void delete(UUID id);
}
