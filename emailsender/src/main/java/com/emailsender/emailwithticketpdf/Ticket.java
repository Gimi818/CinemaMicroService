package com.emailsender.emailwithticketpdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Ticket {
    private String name;
    private String filmTitle;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private BigDecimal ticketPrice;
    private int rowsNumber;
    private int seatInRow;
    private int roomNumber;
    private Long userId;
}
