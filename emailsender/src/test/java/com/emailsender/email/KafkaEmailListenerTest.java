package com.emailsender.email;

import com.emailsender.email.dto.ConfirmationEmail;
import com.emailsender.email.dto.EmailWithTicket;
import com.emailsender.email.dto.TicketDto;
import com.emailsender.email.ticket.Currency;
import com.emailsender.email.ticket.TicketType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.mail.MessagingException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

@SpringBootTest
class KafkaEmailListenerTest {

    @Mock
    private EmailWithTicketPdf emailWithTicketPdf;

    @Mock
    private ConfirmationEmailSender sendConfirmationEmail;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumer kafkaEmailListener;

    private final String emailWithTicketJson = "{\"email\":\"example@example.com\",\"ticketDto\":{\"name\":\"SampleTicket\"}}";
    private final String confirmationEmailJson = "{\"to\":\"user@example.com\",\"confirmationLink\":\"http://example.com/confirm\"}";

    private EmailWithTicket emailWithTicket;
    private ConfirmationEmail confirmationEmail;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        emailWithTicket = new EmailWithTicket("example@example.com", new TicketDto("Wojtek", "FilmTitle", LocalDate.now(), LocalTime.now(), new BigDecimal("12.00"), 2, 10, 1, TicketType.NORMAL, Currency.CLP));
        confirmationEmail = new ConfirmationEmail("user@example.com", "http://example.com/confirm");

        when(objectMapper.readValue(emailWithTicketJson, EmailWithTicket.class)).thenReturn(emailWithTicket);
        when(objectMapper.readValue(confirmationEmailJson, ConfirmationEmail.class)).thenReturn(confirmationEmail);
    }

    @Test
    @DisplayName("Should listen to kafka and send email with pdf")
    void should_send_send_email_with_pdf() throws JsonProcessingException, MessagingException {
        kafkaEmailListener.listenForEmailWithTicket(emailWithTicketJson);

        verify(objectMapper, times(1)).readValue(emailWithTicketJson, EmailWithTicket.class);
        verify(emailWithTicketPdf, times(1)).sendEmailWithPDF(emailWithTicket);
    }

    @Test
    @DisplayName("Should listen to kafka and send confirmation email")
    void should_send_confirmation_email() throws JsonProcessingException, MessagingException {
        kafkaEmailListener.listenConfirmationEmail(confirmationEmailJson);

        verify(objectMapper, times(1)).readValue(confirmationEmailJson, ConfirmationEmail.class);
        verify(sendConfirmationEmail, times(1)).sendConfirmationEmail(confirmationEmail.to(), confirmationEmail.confirmationLink());
    }
}
