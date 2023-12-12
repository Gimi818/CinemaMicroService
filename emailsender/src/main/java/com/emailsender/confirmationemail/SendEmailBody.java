package com.emailsender.confirmationemail;



public record SendEmailBody
        (String to,
         String confirmationLink) {
}

