package com.screening.screening;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.screening.screening.dto.CreatedScreeningDto;
import com.screening.screening.dto.ScreeningRequestDto;
import com.screening.screening.dto.ScreeningResponseDto;
import com.service.film.Film;
import com.service.film.FilmCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ScreeningControllerTest {
    @MockBean
    private ScreeningService screeningService;

    @Autowired
    private WebApplicationContext context;
    private Film film;
    private MockMvc mockMvc;
    private static ScreeningRequestDto screeningRequestDto;
    private static String screeningRequestDtoJson;
    private static Screening screening;
    private static ScreeningResponseDto screeningResponseDto;
    private static ScreeningResponseDto secondScreeningResponseDto;
    private static CreatedScreeningDto createdScreeningDto;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate data = LocalDate.of(2023, 10, 10);
        LocalTime time = LocalTime.of(21, 11);
        Film newFilm = new Film(1L, "AS", FilmCategory.ACTION, 120);
        screeningRequestDto = new ScreeningRequestDto(data, time);
        screeningRequestDtoJson = objectMapper.writeValueAsString(screeningRequestDto);

        screeningResponseDto = new ScreeningResponseDto(1L, data, time, null);
        secondScreeningResponseDto = new ScreeningResponseDto(2L, data, time, null);

    }

    @Test
    @DisplayName("Should save screening")
    void should_save_screening() throws Exception {
        Film newFilm = new Film(1L, "AS", FilmCategory.ACTION, 120);
        given(screeningService.saveScreening(screeningRequestDto, newFilm.getId())).willReturn(createdScreeningDto);

        mockMvc.perform(post("/api/v1/screenings/{filmId}", newFilm.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(screeningRequestDtoJson))

                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should book seats")
    void should_book_seats() throws Exception {
        Long screeningId = 1L;
        int rowNumber = 2;
        int seatsNumber = 3;

        doNothing().when(screeningService).bookingSets(screeningId, rowNumber, seatsNumber);
        mockMvc.perform(put("/api/v1/screenings/booking/seats/{screeningId}/{rowNumber}/{seatsNumber}", screeningId, rowNumber, seatsNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(screeningService, times(1)).bookingSets(screeningId, rowNumber, seatsNumber);
    }


}

