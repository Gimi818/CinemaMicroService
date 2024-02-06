package com.ticket.ticketingSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.common.dto.EmailWithTicket;
import com.ticket.common.dto.TicketDto;
import com.ticket.common.enums.Currency;
import com.ticket.common.enums.TicketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    private final String topic = "ticketTopic";
    private EmailWithTicket emailWithTicket;
    private final String serializedEmailWithTicket = "{\"email\":\"user@example.com\",\"ticketDto\":{...}}";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        TicketDto ticketDto = new TicketDto("Name", "FilmTitle", LocalDate.now(), LocalTime.now(), new BigDecimal("12.00"), 5, 10, 1, TicketType.NORMAL, Currency.USD);
        emailWithTicket = new EmailWithTicket("user@example.com", ticketDto);
        when(objectMapper.writeValueAsString(eq(emailWithTicket))).thenReturn(serializedEmailWithTicket);
    }

    @Test
    @DisplayName("Should serialize object and send email with ticket")
    void should_send_message_to_kafka() throws JsonProcessingException {
        kafkaProducer.sendMessage(topic, emailWithTicket);

        verify(objectMapper, times(1)).writeValueAsString(emailWithTicket);
        verify(kafkaTemplate, times(1)).send(topic, serializedEmailWithTicket);
    }

    @Test
    @DisplayName("Should throw error when serialization is fail ")
    void should_throw_error() throws JsonProcessingException {
        JsonProcessingException exception = new JsonProcessingException("error") {
        };
        doThrow(exception).when(objectMapper).writeValueAsString(any(EmailWithTicket.class));

        kafkaProducer.sendMessage(topic, emailWithTicket);

        verify(objectMapper, times(1)).writeValueAsString(eq(emailWithTicket));
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }
}
