package com.currencies;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExchangeRateScheduler {
    private final ExchangeRateService service;
    private static final long ONE_DAY_IN_MILLISECONDS = 86400000;

    @Scheduled(fixedRate = ONE_DAY_IN_MILLISECONDS)
    public void updateExchangeRate() {
        service.requestToNBP();
    }
}

