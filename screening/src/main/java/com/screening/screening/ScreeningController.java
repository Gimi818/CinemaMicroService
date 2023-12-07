package com.screening.screening;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.screening.screening.ScreeningController.Routes.*;


import com.screening.screening.dto.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScreeningController {
    private final ScreeningService service;

    @GetMapping(ROOT)
    public ResponseEntity<List<ScreeningResponseDto>> getScreeningsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScreeningResponseDto> screenings = service.getScreeningsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(screenings);

    }
    @PostMapping(SAVE)
    public ResponseEntity<CreatedScreeningDto> saveScreening(@RequestBody ScreeningRequestDto screeningDto, @PathVariable Long filmId) {
        return new ResponseEntity<>(service.saveScreening(screeningDto, filmId), HttpStatus.CREATED);
    }


    @GetMapping(FIND_SCREENING)
    public ScreeningResponseDto findById(@PathVariable Long screeningId) {
        return service.getScreeningWithFilm(screeningId);
    }

    //    @GetMapping(FIND_AVAILABLE_SEATS)
//    public ResponseEntity<ScreeningAvailableSeats> findAvailableSeats(@PathVariable Long id) {
//        ScreeningAvailableSeats screeningAvailableSeats = screeningFacade.findAvailableSeats(id);
//        return ResponseEntity.status(HttpStatus.OK).body(screeningAvailableSeats);
//    }

    static final class Routes {
        static final String ROOT = "/api/v1/screenings";
        static final String SAVE = ROOT + "/{filmId}";

        static final String FIND_SCREENING = ROOT + "/{screeningId}";

        static final String FIND_AVAILABLE_SEATS = ROOT + "/{id}";

    }

}
