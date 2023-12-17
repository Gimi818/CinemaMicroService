package com.user.confirmation;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="emailSender-service", url ="${application.config.emailSender-url}" )
public interface EmailSenderClient {

    @PostMapping("/confirmationEmail")
    void sendEmail(@RequestBody SendEmailBody request);
}
