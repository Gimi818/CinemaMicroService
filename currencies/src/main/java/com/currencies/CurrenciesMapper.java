package com.currencies;

import com.currencies.dto.ExchangeRateResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrenciesMapper {
    ExchangeRateResponseDto entityToDto(ExchangeRate exchangeRate);
}
