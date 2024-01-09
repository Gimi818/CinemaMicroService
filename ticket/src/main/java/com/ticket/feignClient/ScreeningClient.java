package com.ticket.feignClient;

import com.ticket.common.dto.ScreeningDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "screening-service", url = "${application.config.screenings-url}")
public interface ScreeningClient {

    @GetMapping("/{screeningId}")
    ScreeningDto findScreeningById(@PathVariable("screeningId") Long screeningId);

    @PutMapping("/booking/seats/{screeningId}/{rowNumber}/{seatsNumber}")
    void bookingSets(@PathVariable Long screeningId, @PathVariable int rowNumber, @PathVariable int seatsNumber);
}
