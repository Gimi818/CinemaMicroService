package com.emailsender.email.dto;



public record ConfirmationEmail
        (String to,
         String confirmationLink) {
}

