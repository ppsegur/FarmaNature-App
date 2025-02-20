package com.salesianostriana.dam.farma_app.util;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

@Service
public class ResendMailSender {
    
    @Value("${resend.api.key}")
    private String resendApiKey;
    private static final Logger log = LoggerFactory.getLogger(ResendMailSender.class);

    private Resend resend;

    @PostConstruct
    public void init() {
        resend = new Resend(resendApiKey);
    }

    @Async
    public void sendMail(String to, String subject, String message, String qrImage) throws IOException, ResendException, java.io.IOException {
        File qrFile = new File(qrImage);
        InputStream qrInputStream = new FileInputStream(qrFile);
        byte[] fileContent = qrInputStream.readAllBytes();
        String base64Content = Base64.getEncoder().encodeToString(fileContent);
        Attachment attachment = Attachment.builder()
                .fileName(qrFile.getName())  // Nombre del archivo
                .content(base64Content)
                .build();
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(to)
                .subject(subject)
                .html(message)
                .attachments(attachment)
                .build();


        CreateEmailResponse data = resend.emails().send(params);


        log.info("Email enviado a {} con ID: {}", to, data.getId());
    }

}








