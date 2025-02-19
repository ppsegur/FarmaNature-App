package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.user.ActivateAccountRequest;
import com.salesianostriana.dam.farma_app.dto.user.CreateUserRequest;
import com.salesianostriana.dam.farma_app.dto.user.LoginRequest;
import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.seguridad.access.JwtService;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshToken;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenRequest;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenService;
import com.salesianostriana.dam.farma_app.servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        Usuario user = userService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user));
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