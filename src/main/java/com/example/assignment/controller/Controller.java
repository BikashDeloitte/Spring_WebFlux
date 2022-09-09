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
    public Flux<ResponseEntity<Movie>> getAllMovies() {
        return movieService.getAllMovies()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /*
    * /movie/{count}/ -> / is add to resolve a problem
    * we are using ResponseEntity<Flux<Movie>> return Type instead Flux<ResponseEntity<Movie>>, Flux<ResponseEntity<Movie>> giving only one element
    * */
    @GetMapping("/movie/{count}/")
    public ResponseEntity<Flux<Movie>> getMovieByTypeAndCountry(@RequestParam("movieType") String movieType, @RequestParam("country") String country, @PathVariable int count) {
        Flux<Movie> movieFlux = movieService.getMovieByTypeAndCountry(movieType, country, count);
        return ResponseEntity.ok(movieFlux);
    }

    @PutMapping("/movie/update/title")
    public Mono<ResponseEntity<Movie>> updateMovieByTitle(@Valid @RequestParam Integer releaseDate, @RequestParam String title) {
        return movieService.updateMovieByTitle(releaseDate, title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/movie/update/showId")
    public Mono<ResponseEntity<Movie>> updateMovieByShowId(@Valid @RequestParam Integer releaseDate, @RequestParam String showId) {
        return movieService.updateMovieByShowId(releaseDate, showId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/movie/{id}")
    public Mono<ResponseEntity<Movie>> getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/movie")
    public Mono<ResponseEntity<Movie>> addMovie(@Valid @RequestBody Movie movie){
        return movieService.addMovie(movie)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
