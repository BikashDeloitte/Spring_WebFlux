package com.example.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Movie {
    @Id
    private Long id;
    @NotNull(message = "Movie.showId can't be null")
    private String showId;

    private String type;
    private String title;
    private String director;

    private List<String> casts;
    private String country;

    private String dateAdded;

    @Positive(message = "Movie.releaseYear should be positive number")
    private Integer releaseYear;
    private String rating;
    private String duration;
    private List<String> listedIn;
    private String description;

    public Movie(String showId, String type, String title, String director, List<String> casts, String country, String dateAdded, Integer releaseYear, String rating, String duration, List<String> listedIn, String description) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.casts = casts;
        this.country = country;
        this.dateAdded = dateAdded;
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
        this.casts = Arrays.asList(values[4].replace("\"","").split(","));
        this.country = values[5];
        this.dateAdded = values[6].replace("\"","");
        this.releaseYear = Integer.parseInt(values[7]);
        this.rating = values[8];
        this.duration = values[9];
        this.listedIn = Arrays.asList(values[10].replace("\"","").split(","));
        this.description = values[11].replace("\"","");
    }
}
