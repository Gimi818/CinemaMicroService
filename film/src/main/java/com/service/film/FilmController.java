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
    @PostMapping(SAVE)
    public ResponseEntity<CreatedFilmDto> saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        return new ResponseEntity<>(filmService.saveFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(ROOT)
    public ResponseEntity<List<FilmResponseDto>> findAll() {
        List<FilmResponseDto> allFilms = filmService.findAllFilms();
        return ResponseEntity.status(HttpStatus.OK).body(allFilms);
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FilmResponseDto> findFilmById(@PathVariable Long id) {
        FilmResponseDto filmResponseDto = filmService.findFilmById(id);
        return ResponseEntity.status(HttpStatus.OK).body(filmResponseDto);
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
        static final String ROOT = "/films";
        static final String SAVE = "/films";
        static final String DELETE_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_CATEGORY = ROOT + "/category";

    }

}
