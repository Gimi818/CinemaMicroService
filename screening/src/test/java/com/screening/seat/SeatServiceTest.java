package com.screening.seat;

import com.screening.common.exception.exceptions.AlreadyTakenException;
import com.screening.common.exception.exceptions.NotFoundException;
import com.screening.screening.Screening;
import com.screening.screening.ScreeningFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SeatServiceTest {

    @Mock
    private ScreeningFacade screeningRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    @DisplayName("Should take the available seat")
    void should_take_available_seat() {
        // Given
        Long screeningId = 1L;
        int rowsNumber = 1;
        int seatInRow = 1;
        Screening screening = new Screening();
        Seat seat = new Seat();
        seat.setStatus(SeatStatus.AVAILABLE);

        when(screeningRepository.findById(screeningId)).thenReturn(screening);
        when(seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow)).thenReturn(seat);

        // When
        seatService.checkSeatsAvailability(screeningId, rowsNumber, seatInRow);

        // Then
        assertEquals(SeatStatus.TAKEN, seat.getStatus());
    }

    @Test
    @DisplayName("Should throw SeatAlreadyException when seat is taken")
    void should_throw_seatAlreadyException() {
        // Given
        Long screeningId = 1L;
        int rowsNumber = 1;
        int seatInRow = 1;
        Screening screening = new Screening();
        Seat seat = new Seat();
        seat.setStatus(SeatStatus.TAKEN);

        when(screeningRepository.findById(screeningId)).thenReturn(screening);
        when(seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow)).thenReturn(seat);

        // Then
        assertThrows(AlreadyTakenException.class, () ->
                seatService.checkSeatsAvailability(screeningId, rowsNumber, seatInRow));
    }

    @Test
    @DisplayName("Should throw SeatNotFoundException when seat  doesn't exist")
    void should_throw_seatNotFoundException() {
        // Given
        Long screeningId = 1L;
        int rowsNumber = 4;
        int seatInRow = 3;
        Screening screening = new Screening();

        when(screeningRepository.findById(screeningId)).thenReturn(screening);
        when(seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow)).thenReturn(null);

        // Then
        assertThrows(NotFoundException.class, () ->
                seatService.checkSeatsAvailability(screeningId, rowsNumber, seatInRow));
    }

    @Test
    @DisplayName("Should create new seats")
    void should_create_new_seats() {
        // Given
        int rowNumber = 5;
        int seatInRow = 5;
        SeatStatus status = SeatStatus.AVAILABLE;

        // When
        Seat seat = seatService.createSeat(rowNumber, seatInRow, status);

        // Then
        assertEquals(rowNumber, seat.getRowsNumber());
        assertEquals(seatInRow, seat.getSeatInRow());
        assertEquals(status, seat.getStatus());
    }
}
