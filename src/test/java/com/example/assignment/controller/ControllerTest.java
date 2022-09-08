package com.example.assignment.controller;

import com.example.assignment.entity.Movie;
import com.example.assignment.service.MovieService;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class ControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    MovieService movieService;

    @Test
    public void getMovieByTypeAndCountryTest() {
        Movie movie = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                "Samuthirakani, Bharath Seeni, Vinoth, Esakki Barath, Chemban Vinod Jose, Gautham Menon, Krisha Kurup, Subiksha",
                "India",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                "Action & Adventure, Dramas, International Movies",
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieService.getMovieByTypeAndCountry("Movie","India",1)).thenReturn(Flux.just(movie));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{count}").queryParam("movieType","Movie").queryParam("country","India").build(1))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void updateMovieByShowIdTest(){
        Movie movie = new Movie(
                "s22",
                "Movie",
                "Goli Soda 2",
                "Vijay Milton",
                "Samuthirakani, Bharath Seeni, Vinoth, Esakki Barath, Chemban Vinod Jose, Gautham Menon, Krisha Kurup, Subiksha",
                "India",
                "September 15, 2018",
                2018,
                "TV-14",
                "128 min",
                "Action & Adventure, Dramas, International Movies",
                "A taxi driver, a gangster and an athlete struggle to better their lives despite obstacles like crooked politicians, evil dons and caste barriers."
        );

        when(movieService.updateMovieByShowId(Mockito.any(),Mockito.anyString())).thenReturn(Mono.just(movie));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/movie/update/showId").queryParam("releaseDate","2090").queryParam("showId","s21").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Movie.class)
                .consumeWith(movieEntityExchangeResult -> {
                    var movieResponse = movieEntityExchangeResult.getResponseBody();
                    assertNotNull(movieResponse);
                });

    }


}