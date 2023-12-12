package com.user.confirmation;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="emailSender-service", url ="http://localhost:8022/api/v1/send" )
public interface EmailSenderClient {

    @PostMapping("/confirmationEmail")
    void sendEmail(@RequestBody SendEmailBody request);
}
