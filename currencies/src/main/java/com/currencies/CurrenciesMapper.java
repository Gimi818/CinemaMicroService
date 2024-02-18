package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrenciesMapper {
    ExchangeRateResponseDto entityToResponseDto(ExchangeRate exchangeRate);

    ExchangeRateDto entityToExchangeRateDto(ExchangeRate exchangeRate);

}
