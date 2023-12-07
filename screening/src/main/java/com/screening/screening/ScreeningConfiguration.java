package com.screening.screening;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ScreeningConfiguration {
    @Bean
    public ScreeningFacade screeningFacade(@Lazy final ScreeningService screeningService) {
        return screeningService;
    }
}
