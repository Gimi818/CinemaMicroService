package com.user.confirmation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.Mockito.*;

@SpringBootTest
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    private final String topic = "emailTopic";
    private ConfirmationEmail confirmationEmail;
    private final String serializedEmail = "{\"email\":\"example@example.com\",\"confirmationLink\":\"http://example.com/confirm\"}";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        confirmationEmail = new ConfirmationEmail("example@example.com", "http://example.com/confirm");
        when(objectMapper.writeValueAsString(confirmationEmail)).thenReturn(serializedEmail);
    }

    @Test
    @DisplayName("Should serialize object and send confirmation email")
    void should_send_message_to_kafka() throws JsonProcessingException {
        kafkaProducer.sendMessage(topic, confirmationEmail);
        verify(objectMapper, times(1)).writeValueAsString(confirmationEmail);
        verify(kafkaTemplate, times(1)).send(topic, serializedEmail);
        verifyNoMoreInteractions(kafkaTemplate, objectMapper);
    }

    @Test
    @DisplayName("Should throw error when serialization is fail ")
    void should_throw_error() throws JsonProcessingException {
        JsonProcessingException exception = new JsonProcessingException("error") {};
        doThrow(exception).when(objectMapper).writeValueAsString(any(ConfirmationEmail.class));

        kafkaProducer.sendMessage(topic, confirmationEmail);

        verify(objectMapper, times(1)).writeValueAsString(confirmationEmail);
        verify(kafkaTemplate, never()).send(anyString(), anyString());

    }
}
