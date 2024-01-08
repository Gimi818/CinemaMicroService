package com.ticket.ticketingSystem;

import com.ticket.common.dto.FilmDto;
import com.ticket.common.dto.ScreeningDto;
import com.ticket.common.dto.TicketBookingDto;
import com.ticket.common.dto.UserResponseDto;
import com.ticket.common.enums.AccountType;
import com.ticket.common.enums.Currency;
import com.ticket.common.enums.TicketStatus;
import com.ticket.common.enums.TicketType;
import com.ticket.common.exception.exceptions.NotFoundException;
import com.ticket.ticketingSystem.ticketPriceCalculator.TicketPriceCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private FilmDto film;
    @Mock
    private TicketPriceCalculator ticketPriceCalculator;
    @Mock
    private TicketBookingDto ticketBookingDto;


    @InjectMocks
    private TicketService ticketService;
    @Mock
    private UserResponseDto user;
    @Mock
    private ScreeningDto screening;
    @Mock
    private TicketPriceCalculator ticketDiscounts;
    @Mock
    TicketMapper mapper;

    @Test
    @DisplayName("Should create new ticket")
    public void should_create_new_ticket() {

        LocalDate date = LocalDate.of(2034, 10, 10);
        LocalTime time = LocalTime.of(10, 10);

        FilmDto film1 = new FilmDto(1L, "TOP GUN", 120);
        ScreeningDto screening1 = new ScreeningDto(1L, date, time, film1);


        UserResponseDto user = new UserResponseDto(1L, "Adam", "New", "aa@.cc", AccountType.UNCONFIRMED);
        TicketBookingDto ticketBookingDto1 = new TicketBookingDto(TicketType.NORMAL, Currency.USD, 1, 1);

        when(ticketPriceCalculator.finalPrice(ticketBookingDto1, screening1)).thenReturn(BigDecimal.valueOf(10));

        Ticket ticket = ticketService.createNewTicket(screening1, user, ticketBookingDto1);

        assertEquals("TOP GUN", ticket.getFilmTitle());
        assertEquals(date, ticket.getScreeningDate());
        assertEquals(time, ticket.getScreeningTime());
        assertEquals("Adam New", ticket.getName());
        assertEquals(TicketStatus.ACTIVE, ticket.getStatus());
        assertEquals(TicketType.NORMAL, ticket.getTicketType());
        assertEquals(1, ticket.getRowsNumber());
        assertEquals(1, ticket.getRoomNumber());
        assertEquals(Currency.USD, ticket.getCurrency());
        assertEquals(1, ticket.getSeatInRow());
    }

    @Test
    @DisplayName("Should throw TicketNotFoundException")
    void should_throw_TicketNotFoundException() {
        // Given
        Long nonExistingTicketId = 5L;

        when(ticketRepository.findById(nonExistingTicketId)).thenReturn(java.util.Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () ->
                ticketService.cancelTicket(nonExistingTicketId)
        );
    }


}
