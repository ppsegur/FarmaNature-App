package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.user.EditClienteDto;
import com.salesianostriana.dam.farma_app.dto.user.EditUserDto;
import com.salesianostriana.dam.farma_app.modelo.Cliente;
import com.salesianostriana.dam.farma_app.modelo.UserRole;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.UsuarioRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepo repo;


    public Usuario editCliente(EditClienteDto editUsuarioDto, String username) {
        Optional<Cliente> usuarioOp = repo.findFirstByUsername(username);
        if(usuarioOp.isEmpty()){
            throw new EntityNotFoundException("No existen usuarios con ese nombre");
        }
        // usuarioOp.get().setUsername(editUsuarioDto.username());
        usuarioOp.get().setEmail(editUsuarioDto.email());
        usuarioOp.get().setEdad(editUsuarioDto.edad());
        usuarioOp.get().setDireccion(editUsuarioDto.direccion());
        usuarioOp.get().setTelefono(editUsuarioDto.telefono());


        return repo.save(usuarioOp.get());
    }
}
