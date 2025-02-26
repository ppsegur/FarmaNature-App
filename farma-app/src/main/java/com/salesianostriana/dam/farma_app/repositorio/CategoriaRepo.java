package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepo extends
        JpaRepository<Categoria, UUID> {
     Categoria findByNombre(String nombre);

}
