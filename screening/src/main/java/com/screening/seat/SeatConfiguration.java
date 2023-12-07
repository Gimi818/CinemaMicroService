package com.screening.seat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
class SeatConfiguration {
    @Bean
    public SeatFacade seatFacade(@Lazy final SeatService seatService) {
        return seatService;
    }
}
