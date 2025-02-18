package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.CreateUserRequest;
import com.salesianostriana.dam.farma_app.modelo.UserRole;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final  UsuarioRepo usuarioRepo;

    //Crear usuario
    public Usuario createUser(CreateUserRequest createUserRequest){
        Usuario user = Usuario.builder()
                .username(createUserRequest.username())
                //Faltaría la contraseña que debemos pasarle antes con el passwordEncode
                .roles(Set.of(UserRole.USER))
                .build();
        return usuarioRepo.save(user);
    }

}

