package com.service.film;

import com.service.film.dto.CreatedFilmDto;
import com.service.film.dto.FilmRequestDto;
import com.service.film.dto.FilmResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
interface FilmMapper {

    FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    FilmResponseDto entityToDto(Film film);

    CreatedFilmDto createdEntityToDto(Film film);
    Film dtoToEntity(FilmRequestDto filmRequestDto);



}
