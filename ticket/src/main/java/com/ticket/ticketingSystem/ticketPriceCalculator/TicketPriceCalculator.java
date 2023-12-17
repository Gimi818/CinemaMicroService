package com.ticket.ticketingSystem.ticketPriceCalculator;

import com.ticket.common.dto.ExchangeRate;
import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;
import com.ticket.common.enums.TicketType;
import com.ticket.feignClient.CurrenciesClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.time.DayOfWeek;

import static com.ticket.ticketingSystem.ticketPriceCalculator.TicketPrice.*;

@Component
@AllArgsConstructor

public class TicketPriceCalculator {
    private final CurrenciesClient currenciesClient;

    public int eventDiscount(ScreeningDto screening) {
        DayOfWeek dayOfWeek = screening.date().getDayOfWeek();

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.TUESDAY) {
            return TICKET_PRICE_WITH_EVENT_REDUCE;
        } else
            return BASIC_TICKET_PRICE;
    }


    public int discountForStudents(TicketBookingDto requestDto, ScreeningDto screening) {
        if (requestDto.ticketType() == TicketType.REDUCE) {
            return eventDiscount(screening) - STUDENT_REDUCE;
        } else
            return eventDiscount(screening);
    }

    public BigDecimal finalPrice(TicketBookingDto ticketBookingDto, ScreeningDto screening) {
        ExchangeRate exchangeRate = currenciesClient.findCode(ticketBookingDto.currency().toString());
        double price = (discountForStudents(ticketBookingDto, screening) / exchangeRate.mid());

        BigDecimal result = BigDecimal.valueOf(price);
        return result.setScale(1, RoundingMode.HALF_UP);
    }

}
