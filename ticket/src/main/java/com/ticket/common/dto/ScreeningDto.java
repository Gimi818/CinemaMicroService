package com.ticket.common.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningDto(Long id, LocalDate date, LocalTime time, FilmDto film) {
}
