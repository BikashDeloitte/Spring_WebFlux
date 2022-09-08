package com.example.assignment.service;

import com.example.assignment.entity.Movie;
import com.example.assignment.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Flux<Movie> getAllMovies() {
       return movieRepository.findAll();
    }

    public void uploadFile() throws FileNotFoundException {
        List<Movie> movieList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/netflix_titles.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(values.length != 12 || values[0].equals("show_id")){
                    continue;
                }
                Movie movie = new Movie(values);
                movieList.add(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Flux.fromIterable(movieList)
                .flatMap(movieRepository::save)
                .subscribe();
    }

    public Flux<Movie> getMovieByTypeAndCountry(String movieType, String country, int count) {
        return movieRepository.findAll()
                .filter(movies ->
                        movies.getCountry().equalsIgnoreCase(country) && movies.getType().equalsIgnoreCase(movieType))
                .take(count);
    }

    public Mono<Movie> updateMovieByTitle(int releaseDate, String title) {
        Mono<Movie> movieMono = movieRepository.findByTitle(title)
                .doOnNext(movie -> movie.setReleaseYear(releaseDate));
        movieMono.flatMap(movieRepository::save);
        return movieMono;
    }

    public Mono<Movie> updateMovieByShowId(int releaseDate, String showId) {
        Mono<Movie> movieMono = movieRepository.findByShowId(showId)
                .doOnNext(movie -> movie.setReleaseYear(releaseDate));
        movieMono.flatMap(movieRepository::save);
        return movieMono;
    }

    public Mono<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }
}