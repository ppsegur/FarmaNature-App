package com.salesianostriana.dam.farma_app.seguridad.refresh;

import com.salesianostriana.dam.farma_app.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {
    @Modifying
    @Transactional
    void deleteByUser(Usuario user);

}