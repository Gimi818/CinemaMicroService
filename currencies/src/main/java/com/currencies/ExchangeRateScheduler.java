package com.currencies;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExchangeRateScheduler {
    private final ExchangeRateService service;

    @Scheduled(fixedRate = 86400000)
    public void updateExchangeRate() {
        service.requestToNBP();
    }
}

