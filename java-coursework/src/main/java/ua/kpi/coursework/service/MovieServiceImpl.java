package ua.kpi.coursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.kpi.coursework.domain.Movie;
import ua.kpi.coursework.domain.User;
import ua.kpi.coursework.repository.MovieRepository;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMoviesByYear(Integer year, Pageable limit) {
        return movieRepository.findMoviesByYear(year, limit);
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre, Pageable limit) {
        return movieRepository.findMoviesByGenre(genre, limit);
    }

    public Integer getTotalByYear(Integer year) {
        return movieRepository.countMoviesByYear(year);
    }

    @Override
    public Movie getMovieById(Integer id) {
        return movieRepository.findMovieById(id);
    }

    @Override
    public Integer getTotalByGenre(String genre) {
        return movieRepository.countMoviesByGenre(genre);
    }

    @Override
    public List<String> getUniqueGenres() {
        return movieRepository.findDistinctGenre();
    }

    @Override
    public List<Integer> getUniqueYears() {
        return movieRepository.findDistinctYear();
    }

    @Override
    public List<Movie> getMoviesByTitleLike(String title, Pageable limit) {
        return movieRepository.findMoviesByTitleContainingIgnoreCase(title, limit);
    }

    @Override
    public void addUserToFavorites(Movie movie, User user) {
        movie.addUserToFavorites(user);
        movieRepository.save(movie);
    }

    @Override
    public void deleteUserFromFavorites(Movie movie, User user) {
        movie.deleteUserFromFavorites(user);
        movieRepository.save(movie);
    }

    @Override
    public void addUserToWatchLater(Movie movie, User user) {
        movie.addUserToWatchLater(user);
        movieRepository.save(movie);
    }

    @Override
    public void deleteUserWatchLater(Movie movie, User user) {
        movie.deleteUserWatchLater(user);
        movieRepository.save(movie);
    }

    @Override
    public void save(Movie movie) {
        movie.setDescription("Desc");
        movie.setBudget("0");
        movie.setRating((float) 0);
        movie.setYear(2000);
        movie.setDurability(120);

        movieRepository.save(movie);
    }
}
