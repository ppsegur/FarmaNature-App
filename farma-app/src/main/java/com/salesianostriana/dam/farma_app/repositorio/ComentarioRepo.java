package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ComentarioRepo extends JpaRepository<Comentario, ComentarioKey>, PagingAndSortingRepository<Comentario, ComentarioKey> {
    @Query(value = "SELECT c FROM Comentario c")
    Page<Comentario> findAll(Pageable pageable);

    //Producto con mas comentarios
    @Query("SELECT c.producto, COUNT(c) as total FROM Comentario c GROUP BY c.producto ORDER BY total DESC")
    List<Object[]> findProductoConMasComentarios();
}
