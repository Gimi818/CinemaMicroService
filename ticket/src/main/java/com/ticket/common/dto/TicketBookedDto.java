package com.ticket.common.dto;

import com.ticket.common.enums.TicketStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketBookedDto(String filmTitle, LocalDate screeningDate,
                              LocalTime screeningTime, int rowsNumber,
                              int seatInRow, TicketStatus status) {
}
