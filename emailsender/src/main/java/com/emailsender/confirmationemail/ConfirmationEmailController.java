package com.emailsender.confirmationemail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.emailsender.confirmationemail.ConfirmationEmailController.Routes.*;
@RestController
@RequiredArgsConstructor
public class ConfirmationEmailController {
    private final ConfirmationEmail confirmationEmail;

    @PostMapping(SEND_EMAIL)
    public void sendEmail(@RequestBody SendEmailBody body) throws MessagingException {
        confirmationEmail.sendConfirmationEmail(body.to(), body.confirmationLink());
    }    static final class Routes {
        static final String ROOT = "/api/v1/send";
        static final String SEND_EMAIL = ROOT + "/confirmationEmail";


    }

}
