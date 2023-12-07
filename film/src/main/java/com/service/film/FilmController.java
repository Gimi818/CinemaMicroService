package com.service.film;

import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.dto.FilmRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.service.film.FilmController.Routes.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @PostMapping(ROOT)
    public ResponseEntity<CreatedFilmDto> saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        return new ResponseEntity<>(filmService.saveFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(ROOT)
    public ResponseEntity<List<FilmResponseDto>> findAll() {
        List<FilmResponseDto> allFilms = filmService.findAllFilms();
        return ResponseEntity.status(HttpStatus.OK).body(allFilms);
    }

    @GetMapping(FIND_BY_ID)
    public Film findById(@PathVariable Long id) {
        return filmService.findById(id);
    }

    @GetMapping(FIND_BY_CATEGORY)
    public ResponseEntity<List<FilmResponseDto>> getFilmsByCategory(@RequestParam("category") FilmCategory filmCategory) {
        List<FilmResponseDto> films = filmService.findFilmByCategory(filmCategory);
        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    static final class Routes {
        static final String ROOT = "/api/v1/films";
        static final String DELETE_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_CATEGORY = ROOT + "/category";

    }

}
