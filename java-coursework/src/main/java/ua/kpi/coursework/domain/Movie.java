package ua.kpi.coursework.domain;

import javax.persistence.*;
import java.util.Set;

@Table(name = "movie_table")
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String title;

    private String description;
    private String budget;
    private Integer durability;
    private String genre;
    private Integer year;
    private String image;
    private Float rating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<User> usersWatchLater;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<User> usersFavorites;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private Set<Share> sharedLinks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Set<User> getUsersWatchLater() {
        return usersWatchLater;
    }

    public void setUsersWatchLater(Set<User> usersWatchLater) {
        this.usersWatchLater = usersWatchLater;
    }

    public Set<User> getUsersFavorites() {
        return usersFavorites;
    }

    public void setUsersFavorites(Set<User> usersFavorites) {
        this.usersFavorites = usersFavorites;
    }

    public void addUserToFavorites(User user) {
        this.usersFavorites.add(user);
    }

    public void deleteUserFromFavorites(User user) {
        this.usersFavorites.remove(user);
    }

    public void addUserToWatchLater(User user) {
        this.usersWatchLater.add(user);
    }

    public void deleteUserWatchLater(User user) {
        this.usersWatchLater.remove(user);
    }
}
