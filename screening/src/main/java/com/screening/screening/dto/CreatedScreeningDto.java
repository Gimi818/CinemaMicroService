package com.screening.screening.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreatedScreeningDto(Long id, LocalDate date, LocalTime time, Long filmId) {
}
