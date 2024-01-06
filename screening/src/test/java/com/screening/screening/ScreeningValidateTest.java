package com.screening.screening;

import com.screening.common.exception.exceptions.NotFoundException;
import com.screening.common.exception.exceptions.TooManyScreeningException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest

class ScreeningValidateTest {
    @Mock
    private ScreeningRepository repository;

    @InjectMocks
    private ScreeningValidate screeningValidate;


    @Test
    @DisplayName("Shouldn't throw ScreeningNotFoundException when data is correct")
    void Shouldn_not_throw_ScreeningNotFoundException() {
        // Given
        LocalDate existingDate = LocalDate.of(2024,12,11);

        when(repository.existsByDate(existingDate)).thenReturn(true);

        // Then
        assertDoesNotThrow(() ->
                screeningValidate.checkCorrectData(existingDate)
        );
    }

    @Test
    @DisplayName("Should throw ScreeningNotFoundException")
    void Should_throw_ScreeningNotFoundException() {
        // Given
        LocalDate nonExistingDate = LocalDate.of(2024,12,11);

        when(repository.existsByDate(nonExistingDate)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () ->
                screeningValidate.checkCorrectData(nonExistingDate)
        );
    }

    @Test
    @DisplayName("Shouldn't throw ScreeningTooManyInOneDayException when screening in one day < 5")
    void should_not_throw_ScreeningTooManyInOneDayException() {
        // Given
        LocalDate date = LocalDate.of(2025,10,10);
        Screening screening = new Screening(date, LocalTime.of(10,10),null,null);
        List<Screening> screenings = new ArrayList<>();

        screenings.add(screening);

        when(repository.findScreeningsByDate(date)).thenReturn(screenings);

        // Then
        assertDoesNotThrow(() ->
                screeningValidate.checkNumberOfScreeningsDuringDay(screenings)
        );
    }


    @Test
    @DisplayName("Should throw ScreeningTooManyInOneDayException when screening in one day > 5")
    void Should_throw_ScreeningTooManyInOneDayException() {
        // Given
        LocalDate date = LocalDate.of(2025,10,10);
        Screening screening = new Screening(date,null,null,null);
        Screening screening2 = new Screening(date,null,null,null);
        Screening screening3 = new Screening(date,null,null,null);
        Screening screening4 = new Screening(date,null,null,null);
        Screening screening5 = new Screening(date,null,null,null);
        Screening screening6 = new Screening(date,null,null,null);
        List<Screening> screenings = new ArrayList<>();
        screenings.add(screening);
        screenings.add(screening2);
        screenings.add(screening3);
        screenings.add(screening4);
        screenings.add(screening5);
        screenings.add(screening6);

        when(repository.findScreeningsByDate(date)).thenReturn(screenings);

        // Then
        assertThrows(TooManyScreeningException.class, () ->
                screeningValidate.checkNumberOfScreeningsDuringDay(screenings)
        );
    }
}
