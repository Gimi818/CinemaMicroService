package com.emailsender.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
@AllArgsConstructor
class ConfirmationEmailSender {
    private final JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String to, String confirmationLink) throws MessagingException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(bundle.getString("registration.subject"));
        String text = bundle.getString("registration.message") +
                "<a href='" + confirmationLink + "'>" + confirmationLink + "</a>";

        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
