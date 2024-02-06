package com.ticket.ticketingSystem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.common.dto.EmailWithTicket;
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


    public void sendMessage(String topic, EmailWithTicket emailWithTicket) {
        try {
            String emailWithTicketJson = objectMapper.writeValueAsString(emailWithTicket);
            kafkaTemplate.send(topic, emailWithTicketJson);
            log.info("Sent emailWithTicket to Kafka topic: {}", topic);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize emailWithTicket object to JSON", e);
        }
    }
}
