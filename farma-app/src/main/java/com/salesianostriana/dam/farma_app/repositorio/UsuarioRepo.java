package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepo extends JpaRepository<Usuario, UUID> {

    //consulta básica para obtener un usuario por el username
    Optional<Usuario> findFirstByUsername(String username);

}
