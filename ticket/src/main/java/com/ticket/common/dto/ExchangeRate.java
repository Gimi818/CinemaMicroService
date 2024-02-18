package com.ticket.common.dto;

public record ExchangeRate(
                           String currency,
                           String code,
                           double mid) {
}
