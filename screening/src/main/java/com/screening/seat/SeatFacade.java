package com.screening.seat;

public interface SeatFacade {
   void checkSeatsAvailability(Long screeningId, int rowsNumber, int seatInRow);

    Seat createSeat(int rowNumber, int seatInRow, SeatStatus status);
}
