package com.currencies.dto;

public record Rate(String currency,
                   String code,
                   double mid) {
}
