package com.screening.screening;

import com.screening.screening.client.FilmClient;
import com.screening.screening.dto.*;
import com.screening.common.exception.exceptions.NotFoundException;
import com.screening.seat.Seat;
import com.screening.seat.SeatFacade;
import com.screening.seat.SeatStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.screening.screening.ScreeningService.ErrorMessages.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Log4j2
class ScreeningService implements ScreeningFacade {
    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;
    private final ScreeningValidate validate;
    private final FilmClient filmClient;
    private final SeatFacade seatFacade;


    @Transactional
    public CreatedScreeningDto saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId) {
        Film film = validateAndGetFilm(filmId);
        validateScreeningData(screeningRequestDto, film);

        Screening screening = createAndSaveScreening(screeningRequestDto, film);
        assignSeatsToScreening(screening);

        log.info("Saved Screening with ID {}", screening.getId());
        return mapper.createdEntityToDto(screening);
    }

    private Film validateAndGetFilm(Long filmId) {
        Film film = filmClient.findFilmById(filmId);
        log.info("Film found: {}", film.id());
        return film;
    }

    public void bookingSets(Long id, int rowNumber, int seatsNumber) {
        seatFacade.checkSeatsAvailability(id, rowNumber, seatsNumber);
    }

    @Transactional
    private void validateScreeningData(ScreeningRequestDto screeningRequestDto, Film film) {
        validate.dataValidation(screeningRequestDto, film);
    }

    private Screening createAndSaveScreening(ScreeningRequestDto screeningRequestDto, Film film) {
        Screening screening = mapper.dtoToEntity(screeningRequestDto);
        screening.setFilmId(film.id());
        return repository.save(screening);
    }

    private void assignSeatsToScreening(Screening screening) {
        List<Seat> seats = createSeats();
        screening.setSeats(seats);
    }

    public ScreeningResponseDto getScreeningWithFilm(Long id) {
        Screening screening = repository.findById(id).orElseThrow();
        ResponseEntity<Film> filmResponse = filmClient.findById(screening.getFilmId());
        Film film = filmResponse.getBody();
        return new ScreeningResponseDto(screening.getId(), screening.getDate(), screening.getTime(), film);
    }


    public List<ScreeningResponseDto> getScreeningsByDate(LocalDate date) {
        validate.checkCorrectData(date);

        List<Screening> screenings = repository.findScreeningsByDate(date);

        return screenings.stream()
                .map(screening -> getScreeningWithFilm(screening.getId()))
                .collect(Collectors.toList());
    }

    public Screening findById(Long screeningId) {
        Screening screening = repository.findById(screeningId).orElseThrow(() -> new NotFoundException(SCREENING_NOT_FOUND, screeningId));
        log.info("Found screening with id {}", screeningId);
        return screening;
    }

    public ScreeningAvailableSeats findAvailableSeats(Long screeningId) {
        Screening screening = repository.findById(screeningId).orElseThrow(() -> new NotFoundException(SCREENING_NOT_FOUND, screeningId));
        log.info("Found screening with id {}", screeningId);
        return mapper.screeningToSeatsDto(screening);
    }

    @Transactional
    private List<Seat> createSeats() {
        return IntStream.rangeClosed(1, 10)
                .boxed()
                .flatMap(rowNumber -> IntStream.rangeClosed(1, 10)
                        .mapToObj(seatInRow -> seatFacade.createSeat(rowNumber, seatInRow, SeatStatus.AVAILABLE)))
                .collect(Collectors.toList());

    }

    static final class ErrorMessages {
        static final String SCREENING_NOT_FOUND = "The screening with id %s not found";

    }

}
