package ua.kpi.coursework.service;

import ua.kpi.coursework.domain.User;

import java.util.Optional;

public interface UserService {
    void save(User user);

    Optional<User> findByUsername(String username);
}