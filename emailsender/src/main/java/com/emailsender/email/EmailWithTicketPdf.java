package com.emailsender.email;

import com.emailsender.email.dto.EmailWithTicket;
import com.emailsender.email.qrCode.GenerateQrCode;
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


    public void sendEmailWithPDF( EmailWithTicket data) throws MessagingException {
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
            Paragraph filmTitle = new Paragraph(data.ticketDto().filmTitle(), titleFont);
            filmTitle.setAlignment(Element.ALIGN_CENTER);

            Font dataFont = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.BLACK);
            Paragraph filmData = new Paragraph(bundle.getString("ticket.date") + data.ticketDto().screeningDate(), dataFont);
            filmData.setAlignment(Element.ALIGN_LEFT);

            Paragraph filmTime = new Paragraph(bundle.getString("ticket.time") + data.ticketDto().screeningTime(), dataFont);
            filmTime.setAlignment(Element.ALIGN_LEFT);


            document.add(filmTitle);
            document.add(filmData);
            document.add(filmTime);
            document.add(new Paragraph(bundle.getString("ticket.name") + data.ticketDto().name()));
            document.add(new Paragraph(bundle.getString("ticket.roomNumber") + data.ticketDto().roomNumber()));
            document.add(new Paragraph(bundle.getString("ticket.row") + data.ticketDto().rowsNumber()));
            document.add(new Paragraph(bundle.getString("ticket.seat") + data.ticketDto().seatInRow()));
            document.add(new Paragraph(bundle.getString("ticket.ticketType") + data.ticketDto().ticketType()));
            document.add(new Paragraph(bundle.getString("ticket.ticketPrice") + data.ticketDto().ticketPrice() + " " + data.ticketDto().currency().toString()));
            document.add(new Paragraph(bundle.getString("ticket.email") + data.email()));
            document.add(new Paragraph(bundle.getString("ticket.purchaseDate") + LocalDate.now()));


            Image qrCodeImage = (Image) generateQrCode.createQr(data.email(), data.ticketDto());
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            qrCodeImage.scaleToFit(280, 280);
            document.add(qrCodeImage);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(data.email());
        helper.setSubject(bundle.getString("email.subject"));

        String text = bundle.getString("email.thankYouMessage");
        helper.setText(text, true);

        byte[] pdfBytes = pdfOutputStream.toByteArray();
        helper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
