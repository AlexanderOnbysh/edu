package ua.kpi.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.coursework.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}
