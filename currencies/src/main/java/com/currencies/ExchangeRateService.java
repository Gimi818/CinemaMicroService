package com.currencies;

import com.currencies.dto.CurrencyDataDto;
import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import com.currencies.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.currencies.ExchangeRateService.ErrorMessages.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
class ExchangeRateService {

    private final ExchangeRateRepository repository;
    private final CurrenciesMapper mapper;


    public ExchangeRateDto findRateByCode(String code) {
        ExchangeRate exchangeRate = repository.findByCode(code).orElseThrow(() -> new NotFoundException(NOT_FOUND_CODE, code));
        return mapper.entityToExchangeRateDto(exchangeRate);
    }

    public List<ExchangeRateResponseDto> findAllCurrency() {
        log.info("Returning all currency");
        return repository.findAll().stream()
                .map(mapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    public void addPLNRate() {
        Optional<ExchangeRate> plnExchangeRate = repository.findByCode("PLN");
        if (plnExchangeRate.isEmpty()) {
            ExchangeRate exchangeRate = new ExchangeRate("Polski Złoty", "PLN", 1.0);
            repository.save(exchangeRate);

            log.info("Added PLN rate   ");
        }
    }
    public void saveCurrencyRates(List<CurrencyDataDto> currencyDataList) {
        currencyDataList.stream()
                .flatMap(currencyData -> currencyData.rates().stream())
                .forEach(rate -> {
                    Optional<ExchangeRate> existingExchangeRate = repository.findByCode(rate.code());
                    if (existingExchangeRate.isPresent()) {
                        ExchangeRate existing = existingExchangeRate.get();
                        existing.setCurrency(rate.currency());
                        existing.setMid(rate.mid());
                        repository.save(existing);
                        log.info("Update {} rate ", existing.getCurrency());
                    } else {
                        ExchangeRate exchangeRate = new ExchangeRate(rate.currency(), rate.code(), rate.mid());
                        repository.save(exchangeRate);
                        log.info("Added {} rate ", exchangeRate.getCurrency());
                    }
                });

        addPLNRate();
    }

    static final class ErrorMessages {
        static final String NOT_FOUND_CODE = "Not found %s code";

    }
}
