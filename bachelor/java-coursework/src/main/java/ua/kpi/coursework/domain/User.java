package ua.kpi.coursework.domain;


import javax.persistence.*;
import java.util.Set;

@Table(name = "user_table")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String login;
    private String password;
    private String passwordConfirm;

    @ManyToMany(mappedBy = "usersWatchLater")
    private Set<Movie> moviesWatchLater;

    @ManyToMany(mappedBy = "usersFavorites")
    private Set<Movie> moviesFavorites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Movie> getMoviesWatchLater() {
        return moviesWatchLater;
    }

    public Set<Movie> getMoviesFavorites() {
        return moviesFavorites;
    }

    public void setMoviesWatchLater(Set<Movie> moviesWatchLater) {
        this.moviesWatchLater = moviesWatchLater;
    }
}
