package com.screening.screening;

import com.screening.common.exception.exceptions.NotFoundException;
import com.screening.common.exception.exceptions.TimeDifferenceException;
import com.screening.common.exception.exceptions.TooLateException;
import com.screening.common.exception.exceptions.TooManyScreeningException;
import com.screening.screening.dto.Film;
import com.screening.screening.dto.ScreeningRequestDto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.screening.screening.ScreeningValidate.ErrorMessages.*;

@Component
@AllArgsConstructor
class ScreeningValidate {
    private final ScreeningRepository repository;

    public void checkCorrectData(LocalDate date) {
        if (!repository.existsByDate(date)) {
            throw new NotFoundException(NOT_FOUND_BY_DATE, date);
        }
    }

    public void checkCorrectTime(ScreeningRequestDto newScreening) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (newScreening.date().isAfter(today)) {
            return;
        }

        if (newScreening.date().isEqual(today) && newScreening.time().isAfter(now)) {
            return;
        }

        throw new TooLateException(TOO_LATE_TO_CREATE);
    }

    public void dataValidation(ScreeningRequestDto screeningRequestDto, Film film) {
        List<Screening> screeningsOnSameDay = getScreeningsByDate(screeningRequestDto.date());

        checkNumberOfScreeningsDuringDay(screeningsOnSameDay);
        checkCorrectTime(screeningRequestDto);
        minTime(screeningRequestDto, film, screeningsOnSameDay);
    }

    private List<Screening> getScreeningsByDate(LocalDate date) {
        return repository.findScreeningsByDate(date);
    }

    private void minTime(ScreeningRequestDto newScreening, Film film, List<Screening> screeningsOnSameDay) {
        for (Screening existingScreening : screeningsOnSameDay) {
            var timeDifference = Duration.between(existingScreening.getTime(), newScreening.time()).toMinutes();
            if (Math.abs(timeDifference) < film.durationFilmInMinutes() + 20) {
                throw new TimeDifferenceException(TOO_SMALL_TIME_DIFFERENCE);
            }
        }
    }

    public void checkNumberOfScreeningsDuringDay(List<Screening> screeningsOnSameDay) {
        if (screeningsOnSameDay.size() >= 5) {
            throw new TooManyScreeningException(TOO_MANY_SCREENINGS);
        }
    }

    static final class ErrorMessages {
        static final String TOO_LATE_TO_CREATE = "It isn't possible to create new screening earlier than the current time";
        static final String NOT_FOUND_BY_DATE = "Screening with date %s not found";
        static final String TOO_MANY_SCREENINGS = "It is impossible to add another screening because there are already five";
        static final String TOO_SMALL_TIME_DIFFERENCE = "Time difference is too small to start a new screening.";

    }
}
