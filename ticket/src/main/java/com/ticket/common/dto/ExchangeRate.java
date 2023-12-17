package com.ticket.common.dto;

public record ExchangeRate(Long id,
                           String currency,
                           String code,
                           double mid) {
}
