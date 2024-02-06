package com.user.confirmation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendMessage(String topic, ConfirmationEmail confirmationEmail) {
        try {
            String confirmationEmailJson = objectMapper.writeValueAsString(confirmationEmail);
            kafkaTemplate.send(topic, confirmationEmailJson);
            log.info("Sent confirmationEmailJson to Kafka topic: {}", topic);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize confirmationEmail object to JSON", e);
        }
    }
}
