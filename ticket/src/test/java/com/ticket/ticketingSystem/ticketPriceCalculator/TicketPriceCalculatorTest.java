package com.ticket.ticketingSystem.ticketPriceCalculator;

import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;
import com.ticket.common.enums.Currency;
import com.ticket.common.enums.TicketType;
import com.ticket.feignClient.CurrenciesClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketPriceCalculatorTest {


    @Mock
    private CurrenciesClient currenciesClient;

    @Test
    @DisplayName("Should return 20 ticket price with event discount")
    void should_return_price_with_discount() {
        // Given
        ScreeningDto screening = new ScreeningDto(1L, LocalDate.of(2023, 11, 3), LocalTime.of(12, 11), null);
        TicketPriceCalculator ticketPriceCalculator = new TicketPriceCalculator(currenciesClient);
        // When
        int result = ticketPriceCalculator.eventDiscount(screening);

        // Then
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Should return 28 ticket  normal price without discount")
    void should_return_basic_price() {
        // Given

        ScreeningDto screening = new ScreeningDto(1L, LocalDate.of(2023, 11, 5), LocalTime.of(12, 11), null);
        TicketPriceCalculator ticketPriceCalculator = new TicketPriceCalculator(currenciesClient);
        // When
        int result = ticketPriceCalculator.eventDiscount(screening);

        // Then
        assertEquals(28, result);
    }

    @Test
    @DisplayName("Should return 15 ticket price with event and student discount")
    void should_return_15_price() {
        // Given
        TicketBookingDto ticketBookingDto = new TicketBookingDto(TicketType.REDUCE, Currency.PLN, 1, 2);
        ScreeningDto screening = new ScreeningDto(1L, LocalDate.of(2023, 11, 3), LocalTime.of(12, 11), null);
        TicketPriceCalculator ticketPriceCalculator = new TicketPriceCalculator(currenciesClient);
        // When
        int result = ticketPriceCalculator.discountForStudents(ticketBookingDto, screening);

        // Then
        assertEquals(15, result);
    }

}


