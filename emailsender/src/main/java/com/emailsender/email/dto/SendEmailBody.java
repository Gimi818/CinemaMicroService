package com.emailsender.email.dto;



public record SendEmailBody
        (String to,
         String confirmationLink) {
}

