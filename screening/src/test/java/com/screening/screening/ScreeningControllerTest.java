package com.screening.screening;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.screening.screening.dto.CreatedScreeningDto;
import com.screening.screening.dto.ScreeningRequestDto;
import com.screening.screening.dto.ScreeningResponseDto;
import com.service.film.Film;
import com.service.film.FilmCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Mock
    LocalDate localDate;
    @Mock
    LocalTime localTime;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Film newFilm = new Film(1L, "AS", FilmCategory.ACTION, 120);
        screeningRequestDto = new ScreeningRequestDto(localDate, localTime);
        screeningRequestDtoJson = objectMapper.writeValueAsString(screeningRequestDto);
        LocalDate data = LocalDate.of(2023, 10, 10);
        LocalTime time = LocalTime.of(21, 11);
        screeningResponseDto = new ScreeningResponseDto(1L, data, time, null);
        secondScreeningResponseDto = new ScreeningResponseDto(2L, data, time, null);

    }

    @Test
    @DisplayName("Should save screening")
    void should_save_screening() throws Exception {
        given(screeningService.saveScreening(screeningRequestDto, 1L)).willReturn(createdScreeningDto);

        mockMvc.perform(post("/api/v1/screenings/1")
                        .content(screeningRequestDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }



//    @Test
//    @DisplayName("Should find all films by date")
//    void should_find_films_by_date() throws Exception {
//        // Given
//        List<ScreeningResponseDto> screeningResponseList = List.of(screeningResponseDto, secondScreeningResponseDto);
//        given(screeningService.getScreeningsByDate(LocalDate.of(2023,10,10)))
//                .willReturn(screeningResponseList);
//
//        // When
//        MvcResult mvcResult = mockMvc.perform(get("/screenings?date=2023-10-10"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String screeningDtoJson = mvcResult.getResponse().getContentAsString();
//        List<Screening> screeningsResult = new ObjectMapper()
//                .readValue(screeningDtoJson, new TypeReference<List<Screening>>() {
//                });
//        assertThat(screeningsResult).hasSize(2);
//
//    }

}
