package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.user.EditClienteDto;
import com.salesianostriana.dam.farma_app.dto.user.EditFarmaceuticoDto;
import com.salesianostriana.dam.farma_app.modelo.*;
import com.salesianostriana.dam.farma_app.repositorio.FarmaceuticoRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FarmaceuticoService {

    private final FarmaceuticoRepo repo;

    public Usuario editFarmaceutico(EditFarmaceuticoDto editUsuarioDto, String username) {
        Optional<Farmaceutico> usuarioOp = repo.findFirstByUsername(username);
        if(usuarioOp.isEmpty()){
            throw new EntityNotFoundException("No existen farmaceuticos con ese nombre");
        }
        // usuarioOp.get().setUsername(editUsuarioDto.username());
        usuarioOp.get().setDireccionLocal(editUsuarioDto.direccionLocal());
        Turno turno = Turno.valueOf(String.valueOf(editUsuarioDto.turno()));

        usuarioOp.get().setTurno(Collections.singleton(editUsuarioDto.turno()));



        return repo.save(usuarioOp.get());
    }
}
