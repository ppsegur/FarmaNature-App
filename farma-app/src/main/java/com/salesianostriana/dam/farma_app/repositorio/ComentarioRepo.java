package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.dto.ProductoComentarioCountDto;
import com.salesianostriana.dam.farma_app.dto.user.ClienteComentarioCountDto;
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
   
@Query("SELECT new com.salesianostriana.dam.farma_app.dto.ProductoComentarioCountDto(c.producto.nombre, COUNT(c)) " +
       "FROM Comentario c GROUP BY c.producto.nombre, c.producto.imagen ORDER BY COUNT(c) DESC")
List<ProductoComentarioCountDto> findProductosConMasComentarios(Pageable pageable);

@Query("SELECT new com.salesianostriana.dam.farma_app.dto.user.ClienteComentarioCountDto(c.cliente.username, c.cliente.nombre, c.cliente.email, COUNT(c)) " +
       "FROM Comentario c GROUP BY c.cliente.username, c.cliente.nombre, c.cliente.email ORDER BY COUNT(c) DESC")
List<ClienteComentarioCountDto> findClientesQueMasComentan(Pageable pageable);

    // Top 3 productos con mas comentarios
    @Query(value = "SELECT c.producto, COUNT(c) as total FROM Comentario c GROUP BY c.producto ORDER BY total DESC")
    List<ProductoComentarioCountDto> findTop3ProductosConMasComentarios(Pageable pageable);


    


}

