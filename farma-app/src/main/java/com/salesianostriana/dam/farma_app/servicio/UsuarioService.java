package com.salesianostriana.dam.farma_app.servicio;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.salesianostriana.dam.farma_app.dto.user.CreateUserRequest;
import com.salesianostriana.dam.farma_app.error.ActivationExpiredException;
import com.salesianostriana.dam.farma_app.modelo.UserRole;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.UsuarioRepo;
import com.salesianostriana.dam.farma_app.util.MailService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthenticator googleAuthenticator;
    private final MailService mailService;

    @Value("${activation.duration}")
    private int activationDuration;

    public Usuario createUser(CreateUserRequest createUserRequest) {

        Usuario user = userRepository.save(Usuario.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .email(createUserRequest.email())
                .roles(Set.of(UserRole.CLIENTE))
                .activationToken(generateRandomActivationCode())
                .build());

        try {
            GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
            user.setSecret(key.getKey());
            String otpAuthURL = generateQRCodeURL(user);
            String qrImagePath = generateQRCodeImage(otpAuthURL);

            // Enviar el correo con la imagen QR
            String emailContent = "<h1>Activación de cuenta</h1>"
                    + "<p>Escanea el siguiente código QR para activar tu cuenta:</p>";

            mailService.sendMail(createUserRequest.email(), "Activación de cuenta", emailContent, qrImagePath);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar el email de activación");
        }

        return userRepository.save(user);
    }


    public String generateQRCodeURL(Usuario user) {
        String issuer = "FarmaNatur-App";
        String accountName = user.getEmail();
        String secret = user.getSecret();

        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                URLEncoder.encode(issuer, StandardCharsets.UTF_8),
                URLEncoder.encode(accountName, StandardCharsets.UTF_8),
                secret,
                URLEncoder.encode(issuer, StandardCharsets.UTF_8)
        );
    }

    public String generateQRCodeImage(String otpAuthURL) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 300, 300);

            String filePath = "src/main/resources/static/qrcode.png";
            java.nio.file.Path path = Paths.get(filePath);
            java.nio.file.Files.createDirectories(path.getParent()); // Crea los directorios si no existen

            // Guardar el QR como archivo PNG
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return filePath;
        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("Error al generar el QR", e);
        }
    }
    public String generateRandomActivationCode() {
        return UUID.randomUUID().toString();
    }

    public Usuario activateAccount(String token) {

        return userRepository.findByActivationToken(token)
                .filter(user -> ChronoUnit.MINUTES.between(Instant.now(), user.getCreatedAt()) - activationDuration < 0)
                .map(user -> {
                    user.setVerificado(true);
                    user.setActivationToken(null);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ActivationExpiredException("El código de activación no existe o ha caducado"));
    }
    public void upDateVerification(Usuario user){

        user.setVerificado(true);
        userRepository.save(user);
    }

    public Usuario findById(UUID id) {
        Optional<Usuario> usuario = userRepository.findById(id);
        if(usuario.isEmpty()){
            throw new EntityNotFoundException("No se han encontrado usuario cone ese id ");
        }
        return usuario.get();
    }
    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Deberíamos hacer dos endPoints para crear un cliente o un farmaceútico
    //Podemos plantearlo paara que cuando sea un farmaceutico el correo de verificacion sea al admin de todas para que hasta que este no confiirme su cuenta no esta verificada
    //y para los usuarios como los clientes podemos hacer el verificado del Qr con 2factor -->

    public List<Usuario> findallUsuarios() {
        List<Usuario> usuarios = userRepository.findAll();
        if(usuarios.isEmpty())
            throw new EntityNotFoundException("No existen usuarios");
        return usuarios;
    }



}

