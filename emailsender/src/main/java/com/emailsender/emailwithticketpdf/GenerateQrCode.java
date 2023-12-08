package com.emailsender.emailwithticketpdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import lombok.AllArgsConstructor;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@AllArgsConstructor
public class GenerateQrCode {

    public Element createQr(String email, Ticket ticket) throws BadElementException, IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        String qrCodeData =
                bundle.getString("ticket.name") + ticket.getName() + "\n"
                        + bundle.getString("ticket.film") + ticket.getFilmTitle() + "\n"
                        + bundle.getString("ticket.date") + ticket.getScreeningDate() + "\n"
                        + bundle.getString("ticket.time") + ticket.getScreeningTime() + "\n"
                        + bundle.getString("ticket.roomNumber") + ticket.getRoomNumber() + "\n"
                        + bundle.getString("ticket.row") + ticket.getRowsNumber() + "\n"
                        + bundle.getString("ticket.seat") + ticket.getSeatInRow() + "\n"
                    //    + bundle.getString("ticket.ticketType") + ticket.getTicketType() + "\n"
                      //  + bundle.getString("ticket.ticketPrice") + ticket.getTicketPrice() + " " + ticket.getCurrency().toString() + "\n"
                        + bundle.getString("ticket.email") + email;
        ByteArrayOutputStream qrOutputStream = QRCode.from(qrCodeData)
                .to(ImageType.PNG)
                .stream();

        return Image.getInstance(qrOutputStream.toByteArray());
    }

}
