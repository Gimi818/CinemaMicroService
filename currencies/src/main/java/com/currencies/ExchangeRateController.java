package com.currencies;

import com.currencies.dto.ExchangeRateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.currencies.ExchangeRateController.Routes.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class ExchangeRateController {
    private final ExchangeRateService service;

    @GetMapping(ROOT)
    public ResponseEntity<List<ExchangeRateResponseDto>> findAllCurrencies() {
        List<ExchangeRateResponseDto> allCurrencies = service.findAllCurrency();
        return ResponseEntity.status(HttpStatus.OK).body(allCurrencies);
    }

    static final class Routes {
        static final String ROOT = "/api/v1/codes";


    }
}
