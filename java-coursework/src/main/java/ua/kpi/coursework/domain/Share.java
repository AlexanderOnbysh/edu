package ua.kpi.coursework.domain;


import javax.persistence.*;
import java.util.Set;

@Table(name = "share_table")
@Entity
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String link;

    @ManyToMany(mappedBy = "sharedLinks")
    private Set<Movie> sharedMovies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public Set<Movie> getSharedMovies() {
        return sharedMovies;
    }

    public void setSharedMovies(Set<Movie> sharedMovies) {
        this.sharedMovies = sharedMovies;
    }

    public void setSharedLink(String link) {
        this.link = link;
    }
}
