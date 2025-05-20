package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.dto.CategoriaProductCount;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepo extends
        JpaRepository<Categoria, UUID> {
     Categoria findByNombre(String nombre);


     @Query("SELECT new com.salesianostriana.dam.farma_app.dto.CategoriaProductCount(c.nombre, COUNT(p)) " +
             "FROM Categoria c LEFT JOIN c.productos p GROUP BY c ORDER BY COUNT(p) DESC")
     List<CategoriaProductCount> contarProductosPorCategoria();

     @Query("SELECT new com.salesianostriana.dam.farma_app.dto.CategoriaProductCount(c.nombre, COUNT(p)) " +
             "FROM Categoria c LEFT JOIN c.productos p GROUP BY c ORDER BY COUNT(p) DESC")
     List<CategoriaProductCount> topCategoriaPorProducto();
}
