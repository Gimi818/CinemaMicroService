package com.user.confirmation;

import com.user.user.AccountType;
import com.user.user.User;
import com.user.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConfirmUser {
    private final UserRepository repository;
    private final KafkaProducer kafkaProducer;

    @Value("${confirmation.link}")
    public String confirmationLink;

    public String generateConfirmationLink(User user) {
        return confirmationLink + user.getConfirmationToken();
    }

    public String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }

    public void sendConfirmationEmail(User user) {
        kafkaProducer.sendMessage("confirmationEmailTopic", new ConfirmationEmail(user.getEmail(), generateConfirmationLink(user)));
    }

    @Transactional
    public void confirmUserAccount(String token) {

        repository.findByConfirmationToken(token).ifPresent(user -> {
            user.setAccountType(AccountType.ACTIVE);
            user.setConfirmationToken(null);

            repository.save(user);
            log.info("Email confirmed {}", user.getEmail());
        });

    }
}
