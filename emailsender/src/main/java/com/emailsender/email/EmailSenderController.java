package com.emailsender.email;

import com.emailsender.email.dto.EmailWithTicket;
import com.emailsender.email.dto.SendEmailBody;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.emailsender.email.EmailSenderController.Routes.*;

@RestController
@RequiredArgsConstructor
public class EmailSenderController {

    private final ConfirmationEmail confirmationEmail;
    private final EmailWithTicketPdf emailWithTicketPdf;


    @PostMapping(BOOKING_TICKET)
    public void sendEmailWithTicket(@RequestBody EmailWithTicket emailWithTicket) throws
            MessagingException {
        emailWithTicketPdf.sendEmailWithPDF(emailWithTicket);


    }
    @PostMapping(SEND_EMAIL)
    public void sendEmail(@RequestBody SendEmailBody body) throws MessagingException {
        confirmationEmail.sendConfirmationEmail(body.to(), body.confirmationLink());

    }


    static final class Routes {
        static final String ROOT = "/api/v1";
        static final String SEND_EMAIL = ROOT + "/confirmationEmail";
        static final String BOOKING_TICKET = ROOT + "/bookingTicket";


    }


}
