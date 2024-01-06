package com.screening.screening;


import com.screening.screening.dto.ScreeningRequestDto;
import com.screening.screening.dto.ScreeningResponseDto;
import com.service.film.FilmCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import static com.service.film.Film.*;
import static com.service.film.FilmCategory.FANTASY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private ScreeningMapper screeningMapper;
    @InjectMocks
    private ScreeningService service;
    @Mock
    private ScreeningRequestDto screeningRequestDto;
    @Mock
    private ScreeningResponseDto screeningResponseDto;
    @Mock
    private ScreeningResponseDto secoundScreeningResponseDto;
    @Mock
    private Screening screening;
    @Mock
    private Screening secoundScreening;
//    @Mock
//    private Film film;
    @Mock
    private ScreeningValidate screeningValidate;
    // @Mock
    // private FilmFacade filmFacade;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Film film = new Film(1L,"Harry Potter", FANTASY, 130);
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2023, 12, 31);
        LocalTime time = LocalTime.of(12, 10);
        screeningRequestDto = new ScreeningRequestDto(date, time);
        screening = new Screening(date, time, null, new ArrayList<>());
        secoundScreening = new Screening(date, time, null, new ArrayList<>());

    }

//        @Test
//    @DisplayName("Should save screening")
//    void should_save_screening() {
//        Film film1 = new Film(1L,"TOP GUN", FilmCategory.ACTION, 120);
//        given(filmFacade.findById(film1.getId())).willReturn(film1);
//
//        given(screeningRepository.save(screeningMapper.dtoToEntity(screeningRequestDto)))
//                .willReturn(screening);
//
//        screening.setFilm(film1);
//        assertThat(service.saveScreening(screeningRequestDto, film1.getId()))
//                .isEqualTo(screening);
//    }

}
