package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepo extends
        JpaRepository<ComentarioKey.Categoria, UUID> {
     ComentarioKey.Categoria findByNombre(String nombre);
}
