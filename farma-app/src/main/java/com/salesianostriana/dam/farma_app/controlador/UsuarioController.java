package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.user.*;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.seguridad.TwoFA.Verify2FARequest;
import com.salesianostriana.dam.farma_app.seguridad.access.JwtService;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshToken;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenRequest;
import com.salesianostriana.dam.farma_app.seguridad.refresh.RefreshTokenService;
import com.salesianostriana.dam.farma_app.servicio.UsuarioService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "El controlador de usuario ")
public class UsuarioController {


    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
    @Autowired
    private  UsuarioService userService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  RefreshTokenService refreshTokenService ;


    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado con éxito",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateUserRequest.class),
                                    examples = @ExampleObject(value = """
                                {
                                    "id": 1,
                                    "username": "Pepe",
                                    "password": "1234",
                                    "verifyPassword": "1234",
                                    "email": "segura.rojos23@triana.salesianos.edu"
                                }
                                """)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para registrar el usuario",
                    content = @Content
            )
    })
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserRequest createUserRequest) {
        Usuario user = userService.createUser(createUserRequest);
        String qrCodeUrl = userService.generateQRCodeURL(user);

        String qrCodeBase64 = userService.generateQRCodeImage(qrCodeUrl); // QR generado en base64

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of( "message", "Usuario creado. Escanea el QR en Google Authenticator.",
                        "qrCodeUrl", qrCodeBase64,
                        "user", UserResponse.of(user)));
    }

    @Operation(summary = "Logea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario logueado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para loguear al usuario ",
                    content = @Content)
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
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

    @Operation(summary = "Crea una nuevo token de refresco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "token de refresco creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenRequest.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el nuevo token de refresco ",
                    content = @Content)
    })
    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));

    }
    @Operation(summary = "Verificado su usuario con exito con el Qr")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario verificado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para verificar el usuario con el código qr",
                    content = @Content)
    })
    @PostMapping("/auth/verify-2fa")
    public ResponseEntity<?> verify2FA(@RequestBody @Valid Verify2FARequest request) {
        Usuario user = userService.findByEmail(request.email());


        if (googleAuthenticator.authorize(user.getSecret(), Integer.parseInt(request.code()))) {
            //user.setVerificado(true);
            userService.upDateVerification(user);

            String accessToken = jwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.create(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserResponse.of(user, accessToken, refreshToken.getToken()));
        }else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al verificar el código 2FA"));
        }
    }
    //Deprecated
    @PostMapping("/activate/account/")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        String token = req.token();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(userService.activateAccount(token)));
    }

    @Operation(summary = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los usuarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Usuario.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                              {
                                                 "username" : "ppsegur",
                                                 "password":"1234",
                                                 "role": "director/admin",                                           \s
                                              }
                                            }
                                           \s"""
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado usuarios"
            )
    })
    @PreAuthorize("hasRole('FARMACEUTICO') (returnObject.owner==authentication.name)")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(GetAllUsuariosDto.fromDto(userService.findallUsuarios()));
    }




    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Usuario user) {
        return UserResponse.of(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me/admin")
    public Usuario adminMe(@AuthenticationPrincipal Usuario user) {
        return user;
    }

}