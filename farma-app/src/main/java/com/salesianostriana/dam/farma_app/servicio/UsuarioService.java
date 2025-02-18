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
    //Deberíamos hacer dos endPoints para crear un cliente o un farmaceútico
    //Podemos plantearlo paara que cuando sea un farmaceutico el correo de verificacion sea al admin de todas para que hasta que este no confiirme su cuenta no esta verificada
    //y para los usuarios como los clientes podemos hacer el verificado del Qr con 2factor -->


    

}

