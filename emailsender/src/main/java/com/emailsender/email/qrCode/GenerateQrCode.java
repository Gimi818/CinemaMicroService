package com.emailsender.email.qrCode;

import com.emailsender.email.dto.TicketDto;
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

    public Element createQr(String email, TicketDto ticket) throws BadElementException, IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        String qrCodeData =
                bundle.getString("ticket.name") + ticket.name() + "\n"
                        + bundle.getString("ticket.film") + ticket.filmTitle() + "\n"
                        + bundle.getString("ticket.date") + ticket.screeningDate() + "\n"
                        + bundle.getString("ticket.time") + ticket.screeningTime() + "\n"
                        + bundle.getString("ticket.roomNumber") + ticket.roomNumber() + "\n"
                        + bundle.getString("ticket.row") + ticket.rowsNumber() + "\n"
                        + bundle.getString("ticket.seat") + ticket.seatInRow() + "\n"
                            + bundle.getString("ticket.ticketType") + ticket.ticketType() + "\n"
                          + bundle.getString("ticket.ticketPrice") + ticket.ticketPrice() + " " + ticket.currency().toString() + "\n"
                        + bundle.getString("ticket.email") + email;
        ByteArrayOutputStream qrOutputStream = QRCode.from(qrCodeData)
                .to(ImageType.PNG)
                .stream();

        return Image.getInstance(qrOutputStream.toByteArray());

    }

}
