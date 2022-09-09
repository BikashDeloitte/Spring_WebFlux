package com.example.assignment.service;

import com.example.assignment.entity.Movie;
import com.example.assignment.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieServiceMock;
    @Mock
    private MovieRepository movieRepositoryMock;

    @Test
    void getMovieByTypeAndCountryTest() {
        Movie movie1 = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                Arrays.asList("Samuthirakani", " Bharath Seeni", " Vinoth", " Esakki Barath", " Chemban Vinod Jose", " Gautham Menon", " Krisha Kurup", " Subiksha"),
                "India",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                Arrays.asList("Action & Adventure", " Dramas", " International Movies"),
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );
        Movie movie2 = new Movie(
                "s21",
                "Movie",
                "Soda 2",
                "Milton",
                Arrays.asList("Samuthirakani", " Bharath Seeni", " Vinoth", " Esakki Barath", " Chemban Vinod Jose", " Gautham Menon", " Krisha Kurup", " Subiksha"),
                "USA",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                Arrays.asList("Action & Adventure", " Dramas", " International Movies"),
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieRepositoryMock.findAll()).thenReturn(Flux.just(movie1, movie2));

        StepVerifier.create(movieServiceMock.getMovieByTypeAndCountry("Movie", "India", 1))
                .expectNext(movie1)
                .verifyComplete();
    }

    @Test
    void uploadFileTest(){
        assertDoesNotThrow(() -> movieServiceMock.uploadFile());
    }

    @Test
    void updateMovieByTitleTest() {
        Movie movie1 = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                Arrays.asList("Samuthirakani", " Bharath Seeni", " Vinoth", " Esakki Barath", " Chemban Vinod Jose", " Gautham Menon", " Krisha Kurup", " Subiksha"),
                "India",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                Arrays.asList("Action & Adventure", " Dramas", " International Movies"),
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieRepositoryMock.findByTitle("Goli Soda 2")).thenReturn(Mono.just(movie1));
        when(movieRepositoryMock.save(movie1)).thenReturn(Mono.just(movie1));

        StepVerifier.create(movieServiceMock.updateMovieByTitle(2010, "Goli Soda 2"))
                .assertNext(movie -> assertEquals(2010, movie.getReleaseYear()))
                .verifyComplete();
    }

    @Test
    void updateMovieByShowIdTest() {
        Movie movie1 = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                Arrays.asList("Samuthirakani", " Bharath Seeni", " Vinoth", " Esakki Barath", " Chemban Vinod Jose", " Gautham Menon", " Krisha Kurup", " Subiksha"),
                "India",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                Arrays.asList("Action & Adventure", " Dramas", " International Movies"),
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieRepositoryMock.findByShowId(Mockito.anyString())).thenReturn(Mono.just(movie1));
        when(movieRepositoryMock.save(movie1)).thenReturn(Mono.just(movie1));

        StepVerifier.create(movieServiceMock.updateMovieByShowId(2010, "s22"))
                .assertNext(movie -> assertEquals(2010, movie.getReleaseYear()))
                .verifyComplete();
    }

}