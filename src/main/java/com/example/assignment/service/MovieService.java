package com.example.assignment.service;

import com.example.assignment.entity.Movie;
import com.example.assignment.exception.NotFoundException;
import com.example.assignment.repository.MovieRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

        //with regex
//        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/netflix_titles.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//                if(values.length != 12 || values[0].equals("show_id")){
//                    continue;
//                }
//                Movie movie = new Movie(values);
//                movieList.add(movie);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        //with csvReader
        List<String[]> list = new ArrayList<>();
        Path filePath = Paths.get("src/main/resources/netflix_titles.csv");
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(6).build()) {
                String[] values;
                while ((values = csvReader.readNext()) != null) {

                    if(values.length != 12 || values[0].equals("show_id")){
                        continue;
                    }
                        Movie movie = new Movie(values);
                        movieList.add(movie);
                    }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Flux.fromIterable(movieList)
                .flatMap(movieRepository::save)
                .subscribe();
    }

    public Flux<Movie> getMovieByTypeAndCountry(String movieType, String country, int count) {
        if(count == 0){
            return Flux.empty();
        }
        return movieRepository.findAll()
                .filter(movies ->
                        movies.getCountry().equalsIgnoreCase(country) && movies.getType().equalsIgnoreCase(movieType))
                .take(count);
//                .defaultIfEmpty(Flux.error(new NotFoundException("Movie not found")));
    }

    public Mono<Movie> updateMovieByTitle(Integer releaseDate, String title) {
        Mono<Movie> movieMono = movieRepository.findByTitle(title)
                .doOnNext(movie -> movie.setReleaseYear(releaseDate));
        movieMono.flatMap(movieRepository::save);
        return movieMono;
    }

    public Mono<Movie> updateMovieByShowId(Integer releaseDate, String showId) {
        Mono<Movie> movieMono = movieRepository.findByShowId(showId)
                .doOnNext(movie -> movie.setReleaseYear(releaseDate));
        movieMono.flatMap(movieRepository::save);
        return movieMono;
    }

    public Mono<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Mono<Movie> addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}