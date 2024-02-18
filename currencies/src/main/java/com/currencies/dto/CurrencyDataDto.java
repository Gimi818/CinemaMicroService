package com.currencies.dto;

import java.util.List;

public record CurrencyDataDto(String table,
                              String no,
                              String effectiveDate,
                              List<ExchangeRateDto> rates) {
}
