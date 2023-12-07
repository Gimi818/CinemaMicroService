package com.screening.screening.client;

import com.screening.screening.dto.Film;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name ="film-service", url = "${application.config.films-url}")
public interface FilmClient {

    @GetMapping("{id}")
    ResponseEntity<Film> findById(@PathVariable ("id") Long id);

    @GetMapping("{id}")
    Film findFilmById(@PathVariable ("id") Long id);

}

