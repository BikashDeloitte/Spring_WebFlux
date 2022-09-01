package com.example.assignment.controller;

import com.example.assignment.entity.Movie;
import com.example.assignment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class controller {

    @Autowired
    MovieService movieService;

    @PostMapping("/movie/upload")
    public ResponseEntity<String> uploadDataToDB() throws Exception {
        try {
            movieService.uploadFile();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully in uploading data");
    }

    @GetMapping("/movie/all")
    public ResponseEntity<Flux<Movie>> getAllMovies() {
        Flux<Movie> movieList = movieService.getAllMovies();
        System.out.println(movieList);
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/movie/{count}")
    public ResponseEntity<Flux<Movie>> getMovieByTypeAndCountry(@RequestParam String movieType, @RequestParam String country, @PathVariable int count) {
        Flux<Movie> movies = movieService.getMovieByTypeAndCountry(movieType, country, count);
        return ResponseEntity.ok(movies);
    }

    @PutMapping("/movie/update/title")
    public ResponseEntity<Mono<Movie>> updateMovieByTitle(@RequestParam String releaseDate, @RequestParam String title) {
        Mono<Movie> movie = movieService.updateMovieByTitle(releaseDate, title);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/movie/update/showId")
    public ResponseEntity<Mono<Movie>> updateMovieByShowId(@RequestParam String releaseDate, @RequestParam String showId) {
        Mono<Movie> movie = movieService.updateMovieByShowId(releaseDate, showId);
        return ResponseEntity.ok(movie);
    }

}
