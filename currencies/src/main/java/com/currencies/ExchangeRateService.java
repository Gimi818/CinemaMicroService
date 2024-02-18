package com.currencies;

import com.currencies.dto.CurrencyData;
import com.currencies.dto.ExchangeRateResponseDto;
import com.currencies.dto.Rate;
import com.currencies.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    @Value("${nbp.api.url}")
    private String url;

    public void requestToNBP() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();
        saveCurrencyRatesFromJson(json);
    }

    public ExchangeRate findRateByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new NotFoundException(NOT_FOUND_CODE, code));
    }


    public void saveCurrencyRatesFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CurrencyData> currencyDataList = objectMapper.readValue(jsonResponse, new TypeReference<List<CurrencyData>>() {
            });

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ExchangeRateResponseDto> findAllCurrency() {
        log.info("Returning all currency");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
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

    static final class ErrorMessages {
        static final String NOT_FOUND_CODE = "Not found %s code";

    }
}
