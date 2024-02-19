package com.currencies.nbpApiClient;

import com.currencies.ExchangeRateProcessor;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ExchangeRateScheduler {
    private final ExchangeRateProcessor exchangeRateProcessor;
    private static final long ONE_DAY_IN_MILLISECONDS = 86400000;

    @Scheduled(fixedRate = ONE_DAY_IN_MILLISECONDS)
    public void updateExchangeRate() {
        exchangeRateProcessor.updateCurrencyRates();
    }
}

