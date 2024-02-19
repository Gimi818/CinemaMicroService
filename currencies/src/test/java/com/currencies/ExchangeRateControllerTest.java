package com.currencies;

import com.currencies.dto.ExchangeRateDto;
import com.currencies.dto.ExchangeRateResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ExchangeRateControllerTest {
    @MockBean
    private ExchangeRateService service;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static ExchangeRateDto exchangeRateDto;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        exchangeRateDto = new ExchangeRateDto("Euro","EUR",4.22);
    }

    @Test
    @DisplayName("Should find rate by code")
    void should_find_rate_by_code() throws Exception {
        String code = "EUR";
        given(service.findRateByCode(code)).willReturn(exchangeRateDto);

        mockMvc.perform(get("/api/v1/codes/EUR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("Euro"))
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.mid").value(4.22));
    }

    @Test
    @DisplayName("Should return all currencies")
    void shouldReturnAllCurrencies() throws Exception {

        List<ExchangeRateResponseDto> expectedCurrencies = List.of(
                new ExchangeRateResponseDto("Euro", "EUR"),
                new ExchangeRateResponseDto("US Dollar", "USD")
        );

        given(service.findAllCurrency()).willReturn(expectedCurrencies);
        mockMvc.perform(get("/api/v1/codes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].currency").value("Euro"))
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[1].currency").value("US Dollar"))
                .andExpect(jsonPath("$[1].code").value("USD"));
    }


}
