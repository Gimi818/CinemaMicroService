package com.screening;

import com.screening.dto.CreatedScreeningDto;
import com.screening.dto.ScreeningRequestDto;
import com.screening.dto.ScreeningResponseDto;
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

    CreatedScreeningDto createdEntityToDto(Screening screening);

}
