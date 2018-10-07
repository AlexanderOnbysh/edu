package ua.kpi.coursework.repository;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.coursework.domain.Share;

import java.util.Optional;

/**
 * Created by Alexander Onbysh 5/22/18
 */
public interface ShareRepository extends CrudRepository<Share, Integer> {
    Optional<Share> findByLink(String link);
}
