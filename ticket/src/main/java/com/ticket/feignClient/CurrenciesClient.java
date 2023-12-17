package com.ticket.feignClient;

import com.ticket.common.dto.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "currencies-service", url = "${application.config.currencies-url}")
public interface CurrenciesClient {

    @GetMapping("/{code}")
    ExchangeRate findCode(@PathVariable String code);
}
