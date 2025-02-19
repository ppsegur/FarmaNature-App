package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.user.CreateUserRequest;
import com.salesianostriana.dam.farma_app.error.ActivationExpiredException;
import com.salesianostriana.dam.farma_app.modelo.UserRole;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {




    private final UsuarioRepo userRepository;
    private final PasswordEncoder passwordEncoder;
   // private final SendGridMailSender mailSender;
    //private final ResendMailSender mailSender;


    @Value("${activation.duration}")
    private int activationDuration;

    public Usuario createUser(CreateUserRequest createUserRequest) {
        Usuario user = Usuario.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .email(createUserRequest.email())
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        try {
            //mailSender.sendMail(createUserRequest.email(), "Activación de cuenta", user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al enviar el email de activación");
        }


        return userRepository.save(user);
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

    //Deberíamos hacer dos endPoints para crear un cliente o un farmaceútico
    //Podemos plantearlo paara que cuando sea un farmaceutico el correo de verificacion sea al admin de todas para que hasta que este no confiirme su cuenta no esta verificada
    //y para los usuarios como los clientes podemos hacer el verificado del Qr con 2factor -->




}

