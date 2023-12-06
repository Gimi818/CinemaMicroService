package com.service.film.dto;

import com.service.film.FilmCategory;

public record FilmRequestDto(String title , FilmCategory category, int durationFilmInMinutes) {
}
