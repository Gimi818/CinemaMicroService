package com.ticket.ticketingSystem;


import com.ticket.common.dto.TicketBookedDto;
import com.ticket.common.dto.TicketBookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
class TicketController {

    private final TicketService service;

    @PostMapping(Routes.BOOKING)
    public ResponseEntity<TicketBookedDto> booking(@PathVariable Long userId,
                                                   @PathVariable Long screeningId,
                                                   @RequestBody TicketBookingDto ticketRequestDto)  {
        return new ResponseEntity<>(service.bookTicket(screeningId, userId, ticketRequestDto), HttpStatus.CREATED);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/book";
        static final String BOOKING = ROOT + "/{userId}/{screeningId}";


    }
}
