package com.screening.screening;

import com.screening.screening.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
interface ScreeningMapper {
    ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);

    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

    ScreeningAvailableSeats screeningToSeatsDto(Screening screening);
    CreatedScreeningDto createdEntityToDto(Screening screening);

}
