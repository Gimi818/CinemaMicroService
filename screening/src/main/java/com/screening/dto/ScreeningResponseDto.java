package com.screening.dto;

import com.screening.Film;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningResponseDto(Long id, LocalDate date, LocalTime time, Film film) {
}