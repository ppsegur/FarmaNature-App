package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.user.ActivateAccountRequest;
import com.salesianostriana.dam.farma_app.dto.user.CreateUserRequest;
import com.salesianostriana.dam.farma_app.dto.user.LoginRequest;
import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.seguridad.TwoFA.Verify2FARequest;
import com.salesianostriana.dam.farma_app.seguridad.access.JwtService;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshToken;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenRequest;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenService;
import com.salesianostriana.dam.farma_app.servicio.UsuarioService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final GoogleAuthenticator googleAuthenticator;

    private final UsuarioService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest createUserRequest) {
        Usuario user = userService.createUser(createUserRequest);
        String qrCodeUrl = userService.generateQRCodeURL(user);

        String qrCodeBase64 = userService.generateQRCodeImage(qrCodeUrl); // QR generado en base64

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of( "message", "Usuario creado. Escanea el QR en Google Authenticator.",
                        "qrCodeUrl", qrCodeBase64,
                        "user", UserResponse.of(user)));
    }

    @GetMapping("/auth/qr-code/{email}")
    public ResponseEntity<String> getQrCode(@PathVariable String email) {
        Usuario user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        String otpAuthURL = userService.generateQRCodeURL(user);
        String qrCodeBase64 = userService.generateQRCodeImage(otpAuthURL);
        return ResponseEntity.ok(qrCodeBase64);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario user = (Usuario) authentication.getPrincipal();
        if(!user.isAccountVerify()){
            return ResponseEntity.status(HttpStatus.OK).body("Codigo 2FA requerido");

        }
        String accessToken = jwtService.generateAccessToken(user);

        // Generar el token de refresco
        RefreshToken refreshToken = refreshTokenService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user, accessToken, refreshToken.getToken()));

    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));

    }
    @PostMapping("/auth/verify-2fa")
    public ResponseEntity<?> verify2FA(@RequestBody Verify2FARequest request) {
        Usuario user = userService.findByEmail(request.email());

        if (googleAuthenticator.authorize(user.getSecret(), request.code())) {
            user.setVerificado(true);
            userService.upDateVerification(user);

            String accessToken = jwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.create(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserResponse.of(user, accessToken, refreshToken.getToken()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código 2FA incorrecto.");
        }
    }
    @PostMapping("/activate/account/")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        String token = req.token();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(userService.activateAccount(token)));
    }


    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Usuario user) {
        return UserResponse.of(user);
    }

    @GetMapping("/me/admin")
    public Usuario adminMe(@AuthenticationPrincipal Usuario user) {
        return user;
    }

}