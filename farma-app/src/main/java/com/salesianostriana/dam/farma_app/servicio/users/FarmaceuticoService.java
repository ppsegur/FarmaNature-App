package com.salesianostriana.dam.farma_app.servicio.users;

import com.salesianostriana.dam.farma_app.dto.user.EditFarmaceuticoDto;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.*;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.users.FarmaceuticoRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.UsuarioRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FarmaceuticoService {

    private final FarmaceuticoRepo repo;
    private final UsuarioRepo r;

    public Usuario editFarmaceutico(EditFarmaceuticoDto editFarmaceuticoDto, String username) {
        Optional<Usuario> usuarioOpt = r.findFirstByUsername(username);
        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNotFoundException("No existen usuarios con ese nombre", HttpStatus.NOT_FOUND);
        }
        if (!(usuarioOpt.get() instanceof Farmaceutico)) {
            throw new IllegalArgumentException("El usuario no es un farmacéutico");
        }
        Farmaceutico farmaceutico = (Farmaceutico) usuarioOpt.get();
        farmaceutico.setDireccion(editFarmaceuticoDto.direccion());

        return r.save(farmaceutico);
    }
}
