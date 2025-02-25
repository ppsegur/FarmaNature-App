package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepo extends JpaRepository<Comentario, ComentarioKey> {



}
