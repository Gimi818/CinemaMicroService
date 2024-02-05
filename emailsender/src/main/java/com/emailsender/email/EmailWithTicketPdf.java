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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
@AllArgsConstructor
public class EmailWithTicketPdf {
    private final JavaMailSender javaMailSender;
    private final GenerateQrCode generateQrCode;


    public void sendEmailWithPDF(EmailWithTicket data) throws MessagingException {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Document document = prepareDocument(data, pdfOutputStream);
        byte[] pdfBytes = pdfOutputStream.toByteArray();
        sendEmail(data.email(), pdfBytes);
    }

    private Document prepareDocument(EmailWithTicket data, ByteArrayOutputStream pdfOutputStream) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, pdfOutputStream);
            document.open();
            addLogo(document);
            addContent(document, bundle, data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return document;
    }

    private void addLogo(Document document) throws DocumentException, IOException {
        String logoPath = "classpath:logo2.png";
        Image logo = Image.getInstance(logoPath);
        logo.scaleToFit(600, 200);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);
    }

    private void addContent(Document document, ResourceBundle bundle, EmailWithTicket data) throws DocumentException, IOException {
        addParagraph(document, data.ticketDto().filmTitle(), 25, Element.ALIGN_CENTER);
        addParagraph(document, bundle.getString("ticket.date") + data.ticketDto().screeningDate(), 20, Element.ALIGN_LEFT);
        addParagraph(document, bundle.getString("ticket.time") + data.ticketDto().screeningTime(), 20, Element.ALIGN_LEFT);
        document.add(new Paragraph(bundle.getString("ticket.name") + data.ticketDto().name()));
        document.add(new Paragraph(bundle.getString("ticket.roomNumber") + data.ticketDto().roomNumber()));
        document.add(new Paragraph(bundle.getString("ticket.row") + data.ticketDto().rowsNumber()));
        document.add(new Paragraph(bundle.getString("ticket.seat") + data.ticketDto().seatInRow()));
        document.add(new Paragraph(bundle.getString("ticket.ticketType") + data.ticketDto().ticketType()));
        document.add(new Paragraph(bundle.getString("ticket.ticketPrice") + data.ticketDto().ticketPrice() + " " + data.ticketDto().currency().toString()));
        document.add(new Paragraph(bundle.getString("ticket.email") + data.email()));
        document.add(new Paragraph(bundle.getString("ticket.purchaseDate") + LocalDate.now()));
        addQrCode(document, data);
    }

    private void addParagraph(Document document, String text, int fontSize, int alignment) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, fontSize, Font.NORMAL, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }

    private void addQrCode(Document document, EmailWithTicket data) throws DocumentException, IOException {
        Image qrCodeImage = (Image) generateQrCode.createQr(data.email(), data.ticketDto());
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        qrCodeImage.scaleToFit(280, 280);
        document.add(qrCodeImage);
    }

    private void sendEmail(String recipientEmail, byte[] pdfBytes) throws MessagingException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject(bundle.getString("email.subject"));
        helper.setText(bundle.getString("email.thankYouMessage"), true);
        helper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
