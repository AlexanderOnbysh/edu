package ua.kpi.coursework.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.kpi.coursework.domain.Movie;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    List<Movie> findMoviesByYear(Integer year, Pageable pageable);

    Integer countMoviesByYear(Integer year);

    Integer countMoviesByGenre(String genre);

    @Query("SELECT DISTINCT m.genre FROM Movie m ORDER BY m.genre")
    List<String> findDistinctGenre();

    @Query("SELECT DISTINCT m.year FROM Movie m ORDER BY m.year DESC")
    List<Integer> findDistinctYear();

    List<Movie> findMoviesByGenre(String genre, Pageable pageable);

    Movie findMovieById(Integer id);

    List<Movie> findMoviesByTitleContainingIgnoreCase(String title, Pageable limit);
}