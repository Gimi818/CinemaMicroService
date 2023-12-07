package com.service.film;

import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-06T22:35:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
class FilmMapperImpl implements FilmMapper {

    @Override
    public FilmResponseDto entityToDto(Film film) {
        if ( film == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        FilmCategory category = null;
        int durationFilmInMinutes = 0;

        id = film.getId();
        title = film.getTitle();
        category = film.getCategory();
        durationFilmInMinutes = film.getDurationFilmInMinutes();

        FilmResponseDto filmResponseDto = new FilmResponseDto( id, title, category, durationFilmInMinutes );

        return filmResponseDto;
    }

    @Override
    public CreatedFilmDto createdEntityToDto(Film film) {
        if ( film == null ) {
            return null;
        }

        Long id = null;
        String title = null;

        id = film.getId();
        title = film.getTitle();

        CreatedFilmDto createdFilmDto = new CreatedFilmDto( id, title );

        return createdFilmDto;
    }

    @Override
    public Film dtoToEntity(FilmRequestDto filmRequestDto) {
        if ( filmRequestDto == null ) {
            return null;
        }

        Film film = new Film();

        film.setTitle( filmRequestDto.title() );
        film.setCategory( filmRequestDto.category() );
        film.setDurationFilmInMinutes( filmRequestDto.durationFilmInMinutes() );

        return film;
    }
}
