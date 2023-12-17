package com.ticket.common.dto;

import com.ticket.common.enums.Currency;
import com.ticket.common.enums.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record TicketDto(String name,
                        String filmTitle,
                        LocalDate screeningDate,
                        LocalTime screeningTime,
                        BigDecimal ticketPrice,
                        int rowsNumber,
                        int seatInRow,
                        int roomNumber,
                        TicketType ticketType,
                        Currency currency) {
}
