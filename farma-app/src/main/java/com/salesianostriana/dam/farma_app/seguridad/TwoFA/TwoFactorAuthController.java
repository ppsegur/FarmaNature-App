package com.salesianostriana.dam.farma_app.seguridad.TwoFA;


import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.users.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/2fa")
@RequiredArgsConstructor
public class TwoFactorAuthController {

    private final TwoFactorAuthService twoFactorAuthService;
    private final UsuarioRepo usuarioRepo;


    //generar el Qr
    @PostMapping("/setup")
    public ResponseEntity<String> setup2FA(@AuthenticationPrincipal Usuario usuario) throws Exception {
        if (usuario.getSecret() == null) {
            String secret = twoFactorAuthService.generateSecretKey();
            usuario.setSecret(secret);
            usuarioRepo.save(usuario);
        }

        String qrUrl = twoFactorAuthService.getQRBarcodeURL(usuario.getEmail(), usuario.getSecret());
        String qrImageBase64 = twoFactorAuthService.generateQRCodeImage(qrUrl);

        return ResponseEntity.ok("QR enviado a tu correo electrónico.");
    }

    //generar el código de 6
    @PostMapping("/verify")
    public ResponseEntity<String> verify2FA(@AuthenticationPrincipal Usuario usuario, @RequestParam String code) {
        Totp totp = new Totp(usuario.getSecret());
        if (totp.verify(code)) {
            usuario.setVerificado(true);
            usuarioRepo.save(usuario);
            return ResponseEntity.ok("Código correcto. 2FA activado.");
        } else {
            return ResponseEntity.badRequest().body("Código incorrecto.");
        }
    }
}

