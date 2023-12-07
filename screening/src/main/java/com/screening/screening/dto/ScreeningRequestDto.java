package com.screening.screening.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScreeningRequestDto  (LocalDate date, LocalTime time  ) {
}
