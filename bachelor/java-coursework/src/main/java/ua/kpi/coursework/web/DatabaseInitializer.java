package ua.kpi.coursework.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.kpi.coursework.repository.MovieRepository;
import ua.kpi.coursework.domain.User;
import ua.kpi.coursework.repository.UserRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public DatabaseInitializer(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User initial = new User();
        initial.setLogin("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        initial.setPassword(encoder.encode("admin"));
//        userRepository.save(initial);

    }
}
