package com.user.confirmation;

import com.user.user.AccountType;
import com.user.user.User;
import com.user.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.UUID;
//@Component
//@RequiredArgsConstructor
//@Log4j2
public class ConfirmUser {
//    private final UserRepository repository;
//    //private final ConfirmationEmailFacade confirmationEmail;
//    @Value("${confirmation.link}")
//    public String confirmationLink;
//
//    public String generateConfirmationLink(User user, String confirmationLink) {
//        return confirmationLink + user.getConfirmationToken();
//    }
//
////    public String generateConfirmationToken() {
////        return UUID.randomUUID().toString();
////    }
////    //todo przeniesc metode do servisu odpowidzialnego za wysyłanie emila
////    public void sendConfirmationEmail(User user) {
////        try {
////            confirmationEmail.sendConfirmationEmail(user.getEmail(), generateConfirmationLink(user, confirmationLink));
////        } catch (MessagingException e) {
////            log.error("Error sending the confirmation email.", e);
////        }
////    }
//
//    public void confirmUserAccount(String token) {
//
//        repository.findByConfirmationToken(token).ifPresent(user -> {
//            user.setAccountType(AccountType.ACTIVE);
//            user.setConfirmationToken(null);
//
//            repository.save(user);
//            log.info("Email confirmed {}", user.getEmail());
//        });
//
//    }
}
