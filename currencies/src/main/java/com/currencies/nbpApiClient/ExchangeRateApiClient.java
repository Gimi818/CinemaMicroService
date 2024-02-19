package com.currencies.nbpApiClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
@Log4j2
public class ExchangeRateApiClient {

    @Value("${nbp.api.url}")
    private String url;

    public String fetchCurrencyRatesFromNBP() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("Fetched currency rates from NBP");
        return response.getBody();
    }
}

