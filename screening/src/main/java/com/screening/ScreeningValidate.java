package com.screening;

import com.screening.dto.ScreeningRequestDto;

import com.screening.exception.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.screening.ScreeningValidate.ErrorMessages.*;

@Component
@AllArgsConstructor
public class ScreeningValidate {
    private final ScreeningRepository repository;

    public void checkCorrectData(LocalDate date) {
        if (!repository.existsByDate(date)) {
            throw new NotFoundException(NOT_FOUND_BY_DATE, date);
        }
    }

    public void checkCorrectTime(ScreeningRequestDto newScreening) {

        if (newScreening.date().isAfter(LocalDate.now())) {
            return;
        } else if (newScreening.date().isBefore(LocalDate.now()) || newScreening.time().isBefore(LocalTime.now())) {
            throw new TooLateException(TOO_LATE_TO_CREATE);
        }
    }

    public void minTime(ScreeningRequestDto newScreening, Film film) {
        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());

        for (Screening existingScreening : screeningsOnSameDay) {
            var timeDifference = Duration.between(existingScreening.getTime(), newScreening.time()).toMinutes();
            if (Math.abs(timeDifference) < film.getDurationFilmInMinutes() + 20) {
                throw new TimeDifferenceException(TOO_SMALL_TIME_DIFFERENCE);
            }
        }
    }

    public void checkNumberOfScreeningsDuringDay(ScreeningRequestDto newScreening) {
        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());
        if (screeningsOnSameDay.size() >= 5) {
            throw new TooManyScreeningException(TOO_MANY_SCREENINGS);
        }

    }

    public void dataValidation(ScreeningRequestDto screeningRequestDto, Film film) {
        checkNumberOfScreeningsDuringDay(screeningRequestDto);
        checkCorrectTime(screeningRequestDto);
        minTime(screeningRequestDto, film);

    }

    static final class ErrorMessages {
        static final String TOO_LATE_TO_CREATE = "It isn't possible to create new screening earlier than the current time";
        static final String NOT_FOUND_BY_DATE = "Screening with date %s not found";
        static final String TOO_MANY_SCREENINGS = "It is impossible to add another screening because there are already five";
        static final String TOO_SMALL_TIME_DIFFERENCE = "Time difference is too small to start a new screening.";

    }
}
