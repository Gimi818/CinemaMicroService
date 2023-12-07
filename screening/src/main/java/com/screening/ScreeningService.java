package com.screening;

import com.screening.dto.CreatedScreeningDto;
import com.screening.dto.ScreeningRequestDto;
import com.screening.dto.ScreeningResponseDto;
import com.screening.exception.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import static com.screening.ScreeningService.ErrorMessages.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
@AllArgsConstructor
@Log4j2
public class ScreeningService {

    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;
    private final ScreeningValidate validate;
   // private final SeatFacade seatFacade;
   // private final FilmFacade filmFacade;


    @Transactional
    public CreatedScreeningDto saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId) {
        Film film = filmFacade.findById(filmId);
        validate.dataValidation(screeningRequestDto, film);

        Screening screening = repository.save(mapper.dtoToEntity(screeningRequestDto));

        screening.setFilm(film);
     //   screening.setSeats(createSeats());
        log.info("Saved Screening {}", screeningRequestDto);
        return mapper.createdEntityToDto(screening);
    }

//    @Transactional
//    private List<Seat> createSeats() {
//        return IntStream.rangeClosed(1, 10)
//                .boxed()
//                .flatMap(rowNumber -> IntStream.rangeClosed(1, 10)
//                        .mapToObj(seatInRow -> seatFacade.createSeat(rowNumber, seatInRow, SeatStatus.AVAILABLE)))
//                .collect(Collectors.toList());
//
//    }

    public List<ScreeningResponseDto> findAllScreenings() {
        log.info("Returning all screenings");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScreeningResponseDto> getScreeningsByDate(LocalDate date) {
        validate.checkCorrectData(date);
        List<Screening> screenings = repository.findScreeningsByDate(date);
        log.info("Returning all screenings by date {}", date);
        return screenings.stream()
                .map(mapper::entityToDto)
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

    static final class ErrorMessages {
        static final String SCREENING_NOT_FOUND = "The screening with id %s not found";

    }
}
