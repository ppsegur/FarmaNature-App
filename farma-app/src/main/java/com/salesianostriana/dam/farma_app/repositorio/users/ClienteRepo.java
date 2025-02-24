package com.salesianostriana.dam.farma_app.repositorio.users;

import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepo extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findFirstByUsername(String username);
}
