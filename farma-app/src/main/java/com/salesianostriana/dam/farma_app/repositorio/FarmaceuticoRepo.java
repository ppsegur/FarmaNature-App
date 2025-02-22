package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FarmaceuticoRepo extends
        JpaRepository<Farmaceutico, UUID> {    Optional<Farmaceutico> findFirstByUsername(String username);


}
