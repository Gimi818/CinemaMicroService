package com.currencies;

import com.currencies.dto.CurrencyDataDto;
import com.currencies.exception.exceptions.JsonParseException;
import com.currencies.nbpApiClient.ExchangeRateApiClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.currencies.ExchangeRateProcessor.ErrorMessages.JSON_PARSE_EXCEPTION;

@Service
@AllArgsConstructor
@Log4j2
public class ExchangeRateProcessor {

    private final ExchangeRateService service;
    private final ExchangeRateApiClient exchangeRateApiClient;

    public void updateCurrencyRates() {
        String jsonResponse = exchangeRateApiClient.fetchCurrencyRatesFromNBP();
        List<CurrencyDataDto> currencyDataList = parseDataFromJson(jsonResponse);
        service.saveCurrencyRates(currencyDataList);
    }

    public List<CurrencyDataDto> parseDataFromJson(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<List<CurrencyDataDto>>() {
            });
        } catch (IOException e) {
            throw new JsonParseException(JSON_PARSE_EXCEPTION, e);
        }
    }
    static final class ErrorMessages {
        static final String JSON_PARSE_EXCEPTION = "Error parsing JSON response";

    }
}
