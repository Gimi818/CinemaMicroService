package com.ticket.feignClient;


import com.ticket.common.dto.EmailWithTicket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emailsender-service", url = "${application.config.emailSender-url}")
public interface EmailSenderClient {


    @PostMapping("/bookingTicket")
    void sendEmailWithTicket(@RequestBody EmailWithTicket emailWithTicket);


}
