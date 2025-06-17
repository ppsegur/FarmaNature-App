package com.salesianostriana.dam.farma_app.repositorio.users;

import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepo extends JpaRepository<Usuario, UUID> , PagingAndSortingRepository<Usuario, UUID> {

    //consulta básica para obtener un usuario por el username
    Optional<Usuario> findFirstByUsername(String username);

    Optional<Usuario> findByActivationToken(String activationToken);

    List<Usuario> findAll( );

    Page<Usuario> findByNombre(String nombre, Pageable pageable);

    //consulta para 2FA
    Usuario findByEmail(String email);

    boolean existsByUsername(String username);


    Optional<Usuario> findByUsername(String username);
}
