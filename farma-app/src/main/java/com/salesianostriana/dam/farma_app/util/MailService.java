package com.salesianostriana.dam.farma_app.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String to, String subject, String message, String qrImagePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true); // true para enviar HTML
            helper.setFrom("tu_correo@gmail.com");

            // Adjuntar imagen QR si existe
            if (qrImagePath != null) {
                File qrFile = new File(qrImagePath);
                if (qrFile.exists()) {
                    helper.addAttachment(qrFile.getName(), qrFile);
                }
            }

            mailSender.send(mimeMessage);
            System.out.println("Correo enviado a: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }

}





