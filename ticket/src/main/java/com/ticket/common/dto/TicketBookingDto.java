package com.ticket.common.dto;

import com.ticket.common.enums.Currency;
import com.ticket.common.enums.TicketType;

public record TicketBookingDto(TicketType ticketType, Currency currency, int rowsNumber,
                               int seatInRow) {
}
