package com.example.assignment.repository;

import com.example.assignment.entity.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {

    Mono<Movie> findByShowId(String ShowId);
    Mono<Movie> findByTitle(String title);
}
