package com.example.assignment.controller;

import com.example.assignment.entity.Movie;
import com.example.assignment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class Controller {

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
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/movie/{count}/")
    public ResponseEntity<Flux<Movie>> getMovieByTypeAndCountry(@RequestParam String movieType, @RequestParam String country, @PathVariable int count) {
        Flux<Movie> movies = movieService.getMovieByTypeAndCountry(movieType, country, count);
        return ResponseEntity.ok(movies);
    }

//    @Valid
    @PutMapping("/movie/update/title")
    public ResponseEntity<Mono<Movie>> updateMovieByTitle(@Valid @RequestParam int releaseDate, @RequestParam String title) {
        Mono<Movie> movie = movieService.updateMovieByTitle(releaseDate, title);
        return ResponseEntity.ok(movie);
    }

//    @Valid
    @PutMapping("/movie/update/showId")
    public ResponseEntity<Mono<Movie>> updateMovieByShowId(@Valid @RequestParam int releaseDate, @RequestParam String showId) {
        Mono<Movie> movie = movieService.updateMovieByShowId(releaseDate, showId);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Mono<Movie>> getMovieById(@PathVariable Long id){
        Mono<Movie> movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

}
