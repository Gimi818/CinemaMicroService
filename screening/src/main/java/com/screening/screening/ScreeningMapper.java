package com.screening.screening;

import com.screening.screening.dto.CreatedScreeningDto;
import com.screening.screening.dto.ScreeningFilmDto;
import com.screening.screening.dto.ScreeningRequestDto;
import com.screening.screening.dto.ScreeningResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
interface ScreeningMapper {
    ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);

    ScreeningResponseDto entityToDto(Screening screening);

    // ScreeningAvailableSeats screeningToSeatsDto(Screening screening);

    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);
    ScreeningResponseDto screeningFilmDtoToResponseDto(ScreeningFilmDto screeningFilmDto);

    Screening screeningFilmDtoToResponseDto2(ScreeningFilmDto screeningFilmDto);
    CreatedScreeningDto createdEntityToDto(Screening screening);

}
