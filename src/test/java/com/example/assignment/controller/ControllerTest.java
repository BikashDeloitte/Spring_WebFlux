package com.example.assignment.controller;

import com.example.assignment.entity.Movie;
import com.example.assignment.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class ControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Mock
    MovieRepository movieRepository;

    @Test
    public void getMovieByTypeAndCountryTest() {
        Movie movie1 = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                "Samuthirakani, Bharath Seeni, Vinoth, Esakki Barath, Chemban Vinod Jose, Gautham Menon, Krisha Kurup, Subiksha",
                "India",
                "September 15, 2018",
                "2018",
                "TV-14",
                "128 min",
                "Action & Adventure, Dramas, International Movies",
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );
        Movie movie2 = new Movie(
                "s21",
                "Movie",
                "Soda 2",
                "Milton",
                "Samuthirakani, Bharath Seeni, Vinoth, Esakki Barath, Chemban Vinod Jose, Gautham Menon, Krisha Kurup, Subiksha",
                "USA",
                "September 15, 2018",
                "2018",
                "TV-14",
                "128 min",
                "Action & Adventure, Dramas, International Movies",
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieRepository.findAll()).thenReturn(Flux.just(movie1, movie2));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{count}")
                        .queryParam("movieType", "Movie")
                        .queryParam("country", "India")
                        .build(1))
                .exchange()
                .expectStatus()
                .isOk();
    }
}