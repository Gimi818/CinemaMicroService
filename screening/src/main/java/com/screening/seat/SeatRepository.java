package com.screening.seat;

import com.screening.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByScreeningAndRowsNumberAndSeatInRow(Screening screening, int rowsNumber, int seatInRow);
}
