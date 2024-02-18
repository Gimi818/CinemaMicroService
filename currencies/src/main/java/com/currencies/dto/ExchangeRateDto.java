package com.currencies.dto;

public record ExchangeRateDto(String currency,
                              String code,
                              double mid) {
}
