package com.screening.screening.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningResponseDto(Long id, LocalDate date, LocalTime time, Film film) {
}
