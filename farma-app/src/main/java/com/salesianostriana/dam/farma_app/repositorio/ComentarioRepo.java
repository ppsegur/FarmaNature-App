package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepo extends JpaRepository<Comentario, ComentarioKey> {


    @Query("SELECT c FROM Comentario c WHERE c.cliente.nombre = :cliente_nombre")
    List<Comentario> findByClienteNombre(@Param("cliente_nombre") String nombre);
}
