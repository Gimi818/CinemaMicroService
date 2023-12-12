package com.emailsender.emailwithticketpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@AllArgsConstructor
public class EmailWithTicketPdf {
    private final JavaMailSender javaMailSender;
    private final GenerateQrCode generateQrCode;


    public void sendEmailWithPDF(String email, Ticket ticket) throws MessagingException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        Document document = new Document();
        String logoPath = "classpath:logo2.png";
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, pdfOutputStream);
            document.open();

            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(600, 200);
            logo.setSpacingBefore(0);
            logo.setAlignment(Element.ALIGN_CENTER);

            document.add(logo);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 25, Font.NORMAL, BaseColor.BLACK);
            Paragraph filmTitle = new Paragraph(ticket.getFilmTitle(), titleFont);
            filmTitle.setAlignment(Element.ALIGN_CENTER);

            Font dataFont = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.BLACK);
            Paragraph filmData = new Paragraph(bundle.getString("ticket.date") + ticket.getScreeningDate(), dataFont);
            filmData.setAlignment(Element.ALIGN_LEFT);

            Paragraph filmTime = new Paragraph(bundle.getString("ticket.time") + ticket.getScreeningTime(), dataFont);
            filmTime.setAlignment(Element.ALIGN_LEFT);


            document.add(filmTitle);
            document.add(filmData);
            document.add(filmTime);
            document.add(new Paragraph(bundle.getString("ticket.name") + ticket.getName()));
            document.add(new Paragraph(bundle.getString("ticket.roomNumber") + ticket.getRoomNumber()));
            document.add(new Paragraph(bundle.getString("ticket.row") + ticket.getRowsNumber()));
            document.add(new Paragraph(bundle.getString("ticket.seat") + ticket.getSeatInRow()));
         //   document.add(new Paragraph(bundle.getString("ticket.ticketType") + ticket.getTicketType()));
         //   document.add(new Paragraph(bundle.getString("ticket.ticketPrice") + ticket.getTicketPrice() + " " + ticket.getCurrency().toString()));
            document.add(new Paragraph(bundle.getString("ticket.email") + email));
            document.add(new Paragraph(bundle.getString("ticket.purchaseDate") + LocalDate.now()));


            Image qrCodeImage = (Image) generateQrCode.createQr(email, ticket);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            qrCodeImage.scaleToFit(280, 280);
            document.add(qrCodeImage);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(bundle.getString("email.subject"));

        String text = bundle.getString("email.thankYouMessage");
        helper.setText(text, true);

        byte[] pdfBytes = pdfOutputStream.toByteArray();
        helper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
