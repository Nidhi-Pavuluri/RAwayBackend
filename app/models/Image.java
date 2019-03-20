package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @JsonProperty("imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="id")

    @JsonIgnore
    private Home home;

    public Image() {
    }

    public Image(String imageUrl, Home home) {
        this.imageUrl = imageUrl;
        this.home = home;
    }

    public Image(String imageUrl){

        this.imageUrl=imageUrl;
    }

    @Override
    public String toString() {
        return "img url IS "+ imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}

