package com.service.film.dto;

import com.service.film.FilmCategory;

public record FilmResponseDto (Long id, String title, FilmCategory category, int durationFilmInMinutes) {
}
