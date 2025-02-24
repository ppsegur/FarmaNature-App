package com.salesianostriana.dam.farma_app.repositorio.users;

import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FarmaceuticoRepo extends
        JpaRepository<Farmaceutico, UUID> {    Optional<Farmaceutico> findFirstByUsername(String username);


}
