package com.salesianostriana.dam.farma_app.seguridad.TwoFA;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.jsonwebtoken.io.IOException;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TwoFactorAuthService {

    private static final String ISSUER = "FarmaNatur-App";

    public String generateSecretKey() {
        return Base32.random();
    }

    public String getQRBarcodeURL(String userEmail, String secret) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", ISSUER, userEmail, secret, ISSUER);
    }

    public String generateQRCodeImage(String barcodeData) throws WriterException, IOException, java.io.IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(barcodeData, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

}