package com.emailsender.email;

import com.emailsender.email.dto.ConfirmationEmail;
import com.emailsender.email.dto.EmailWithTicket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final EmailWithTicketPdf emailWithTicketPdf;
    private final ConfirmationEmailSender sendConfirmationEmail;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "emailWithTicketTopic", groupId = "emailSender-group-ticket")
    public void listenForEmailWithTicket(String emailWithTicketJson) {
        try {
            EmailWithTicket emailWithTicket = objectMapper.readValue(emailWithTicketJson, EmailWithTicket.class);
            emailWithTicketPdf.sendEmailWithPDF(emailWithTicket);
        } catch (JsonProcessingException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "confirmationEmailTopic", groupId = "emailSender-group-confirmation")
    public void listenConfirmationEmail(String confirmationEmailJson) {
        try {
            ConfirmationEmail confirmationEmail = objectMapper.readValue(confirmationEmailJson, ConfirmationEmail.class);
            sendConfirmationEmail.sendConfirmationEmail(confirmationEmail.to(), confirmationEmail.confirmationLink());
        } catch (JsonProcessingException | MessagingException e) {
            e.printStackTrace();
        }
    }


}
