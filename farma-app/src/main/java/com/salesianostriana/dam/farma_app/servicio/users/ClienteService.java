package com.salesianostriana.dam.farma_app.servicio.users;

import com.salesianostriana.dam.farma_app.dto.user.EditClienteDto;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.UsuarioRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepo repo;
    private final UsuarioRepo r;


    public Usuario editCliente(EditClienteDto editUsuarioDto, String username) {
        Optional<Usuario> usuarioOpt = r.findFirstByUsername(username);

        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNotFoundException("No existen usuarios con ese nombre", HttpStatus.NOT_FOUND);
        }

        if (!(usuarioOpt.get() instanceof Cliente)) {
            throw new IllegalArgumentException("El usuario no es un cliente");
        }

        Cliente cliente = (Cliente) usuarioOpt.get();

        cliente.setEmail(editUsuarioDto.email());
        cliente.setEdad(editUsuarioDto.edad());
        cliente.setDireccion(editUsuarioDto.direccion());
        cliente.setTelefono(editUsuarioDto.telefono());

       return r.save(cliente);
    }
}
