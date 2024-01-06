package com.service.film;

import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import com.service.film.exception.exceptions.AlreadyExistException;
import com.service.film.exception.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.service.film.FilmCategory.ACTION;
import static com.service.film.FilmCategory.FANTASY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;
    @Mock
    private FilmMapper filmMapper;
    @InjectMocks
    private FilmService service;
    @Mock
    private FilmRequestDto filmRequestDto;
    @Mock
    private FilmResponseDto filmResponseDto;
    @Mock
    private FilmResponseDto secoundFilmResponseDto;
    @Mock
    private CreatedFilmDto createdFilmDto;
    @Mock
    private Film film;
    @Mock
    private Film secoundFilm;

    @Mock
    private FilmCategory filmCategory;


    @BeforeEach
    void setUp() {
        filmRequestDto = new FilmRequestDto("Harry Potter", FANTASY, 130);
        film = new Film(1L, "Harry Potter", FANTASY, 130);
        createdFilmDto = new CreatedFilmDto(1L, "Harry Potter");
        secoundFilm = new Film(2L, "John Wick", ACTION, 110);
    }

    @Test
    @DisplayName("Should save film")
    void should_save_film() {
        given(filmRepository.save(filmMapper.dtoToEntity(filmRequestDto)))
                .willReturn(film);
        assertThat(service.saveFilm(filmRequestDto))
                .isEqualTo(filmMapper.createdEntityToDto(film));
    }


    @Test
    @DisplayName("Should find all films by category")
    void should_find_all_films_by_category() {
        filmResponseDto = new FilmResponseDto(1L, "Harry Potter", ACTION, 130);

        List<Film> filmsList = List.of(film);
        List<FilmResponseDto> expectedFilmsDtoList = List.of(filmResponseDto);

        given(filmRepository.findByCategory(FANTASY)).willReturn(filmsList);
        given(filmMapper.entityToDto(film)).willReturn(filmResponseDto);


        List<FilmResponseDto> actualFilmsDtoList = service.findFilmByCategory(FANTASY);
        Assertions.assertThat(expectedFilmsDtoList).isEqualTo(actualFilmsDtoList);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(film);
    }

    @Test
    @DisplayName("Should find all films")
    void should_find_all_films() {
        filmResponseDto = new FilmResponseDto(1L, "Harry Potter", FANTASY, 130);
        secoundFilmResponseDto = new FilmResponseDto(2L, "John Wick", ACTION, 110);

        List<Film> filmsList = List.of(film, secoundFilm);
        List<FilmResponseDto> expectedFilmsDtoList = List.of(filmResponseDto, secoundFilmResponseDto);

        given(filmRepository.findAll()).willReturn(filmsList);
        given(filmMapper.entityToDto(film)).willReturn(filmResponseDto);
        given(filmMapper.entityToDto(secoundFilm)).willReturn(secoundFilmResponseDto);

        List<FilmResponseDto> actualFilmsDtoList = service.findAllFilms();

        Assertions.assertThat(expectedFilmsDtoList).isEqualTo(actualFilmsDtoList);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(film);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(secoundFilm);
    }


    @Test
    @DisplayName("Should throw filmNotFoundException when film id doesn't exist ")
    void should_throw_filmNotFoundException() {
        // Given
        Long nonExistingFilmId = 10L;

        when(filmRepository.findById(nonExistingFilmId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> service.findFilmById(nonExistingFilmId));
    }

    @Test
    @DisplayName("Should find film with mapping by id")
    void should_find_film_by_id() {
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        given(filmMapper.entityToDto(film))
                .willReturn(filmResponseDto);

        assertThat(service.findFilmById(1L)).isEqualTo(filmResponseDto);
    }

    @Test
    @DisplayName("Should find film by id")
    void should_find_film_by_id2() {
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        assertThat(service.findById(1L)).isEqualTo(film);
    }

    @Test
    @DisplayName("Should delete film by id")
    void should_delete_film() {
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        service.deleteFilm(1L);
        Mockito.verify(filmRepository, Mockito.times(1)).deleteById(1L);

    }


    @Test
    @DisplayName("Should throw exception when film exist by title ")
    void should_throw_exception() {
        // Given
        FilmRequestDto requestDto = new FilmRequestDto("TOP GUN", FilmCategory.HORROR, 122);

        when(filmRepository.existsByTitle("TOP GUN")).thenReturn(true);

        // Then
        assertThrows(AlreadyExistException.class, () ->
                service.existByTitle(requestDto)
        );
    }

    @Test
    @DisplayName("shouldn't throw  exception when film doesn't exist  by title")
    void should_not_throw_exception() {
        // Given
        String nonExistingTitle = "AABBCC";
        FilmRequestDto requestDto = new FilmRequestDto(nonExistingTitle, FilmCategory.HORROR, 110);

        when(filmRepository.existsByTitle(nonExistingTitle)).thenReturn(false);

        // Then
        assertDoesNotThrow(() ->
                service.existByTitle(requestDto)
        );
    }
}
