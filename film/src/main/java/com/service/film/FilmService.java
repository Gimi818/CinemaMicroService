package com.service.film;

import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.AlreadyExistException;
import com.service.film.exception.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import static com.service.film.FilmService.ErrorMessages.*;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Log4j2
public class FilmService {
    private final FilmRepository repository;
    private final FilmMapper mapper;

    @Transactional
    public CreatedFilmDto saveFilm(FilmRequestDto filmRequestDto) {
        existByTitle(filmRequestDto);
        Film film = repository.save(mapper.dtoToEntity(filmRequestDto));
        log.info("Saved film {}", filmRequestDto);
        return mapper.createdEntityToDto(film);
    }

    public FilmResponseDto findFilmById(Long id) {
        Film film = repository.findById(id).orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND,id));
        log.info("Found film with ID {}", id);
        return mapper.entityToDto(film);
    }

    public Film findById(Long id) {
        Film film = repository.findById(id).orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND,id));
        log.info("Found film with ID {}", id);
        return film;
    }

    public List<FilmResponseDto> findAllFilms() {
        log.info("Returning all films");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<FilmResponseDto> findFilmByCategory(FilmCategory filmCategory) {
        List<Film> films = repository.findByCategory(filmCategory);
        log.info("Found {} films.", films.size());
        log.info("Returning all films by category {}", filmCategory);
        return films.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFilm(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND,id));
        log.info("Film with id {} deleted", id);
        repository.deleteById(id);
    }

    public void existByTitle(FilmRequestDto requestDto) {
        if (repository.existsByTitle(requestDto.title())) {
            throw new AlreadyExistException(ErrorMessages.FILM_ALREADY_EXIST, requestDto.title());
        }
    }


    static final class ErrorMessages {
        static final String FILM_ALREADY_EXIST = "The film with title %s already exists.";
        static final String FILM_NOT_FOUND = "The film with id %s not found";

    }
}
