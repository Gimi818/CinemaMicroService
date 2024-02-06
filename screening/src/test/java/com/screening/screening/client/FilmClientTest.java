package com.screening.screening.client;


import com.screening.screening.dto.Film;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class FilmClientTest {
    @Mock
    private FilmClient filmClient;

    @Test
    void testFindById() {

        Film expectedFilm = new Film(1L, "title", 123);
        ResponseEntity<Film> responseEntity = new ResponseEntity<>(expectedFilm, HttpStatus.OK);

        given(filmClient.findById(expectedFilm.id())).willReturn(responseEntity);

        ResponseEntity<Film> result = filmClient.findById(expectedFilm.id());


        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedFilm, result.getBody());
    }

    @Test
    void testFindFilmById() {
        Film expectedFilm = new Film(1L, "title", 123);

        given(filmClient.findFilmById(expectedFilm.id())).willReturn(expectedFilm);


        Film result = filmClient.findFilmById(expectedFilm.id());


        assertEquals(expectedFilm, result);
    }
}
