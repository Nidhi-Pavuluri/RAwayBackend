package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("raringId")
    private Integer ratingId;

    @ManyToOne
    @JoinColumn(name = "homeId")
    @JsonIgnore
    private Home home;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @Basic
    @JsonProperty("rating")
    private Integer rating;

    public Rating() {
    }

    public Rating(Integer ratingId, Home home, User user, Integer rating) {
        this.ratingId = ratingId;
        this.home = home;
        this.user = user;
        this.rating = rating;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
