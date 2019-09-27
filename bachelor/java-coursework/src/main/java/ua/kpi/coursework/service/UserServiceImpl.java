package ua.kpi.coursework.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kpi.coursework.domain.User;
import ua.kpi.coursework.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder PasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder PasswordEncoder) {
        this.userRepository = userRepository;
        this.PasswordEncoder = PasswordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(PasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByLogin(username);
    }
}
