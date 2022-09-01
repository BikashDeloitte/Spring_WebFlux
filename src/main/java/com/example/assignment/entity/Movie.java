package com.example.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Movie {
    @Id
    private Long id;
    private String showId;

    private String type;
    private String title;
    private String director;

    private String casts;
    private String country;

    private String date_added;

    private String releaseYear;
    private String rating;
    private String duration;
    private String listedIn;
    private String description;

    public Movie(String showId, String type, String title, String director, String casts, String country, String date_added, String releaseYear, String rating, String duration, String listedIn, String description) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.casts = casts;
        this.country = country;
        this.date_added = date_added;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = listedIn;
        this.description = description;
    }

    public Movie(String[] values) {
        this.showId = values[0];
        this.type = values[1];
        this.title = values[2];
        this.director = values[3];
        this.casts = values[4];
        this.country = values[5];
        this.date_added = values[6];
        this.releaseYear = values[7];
        this.rating = values[8];
        this.duration = values[9];
        this.listedIn = values[10];
        this.description = values[11];
    }
}
