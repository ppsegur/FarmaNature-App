package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepo extends
        JpaRepository<Producto, UUID> , PagingAndSortingRepository<Producto, UUID>,
        JpaSpecificationExecutor<Producto> {
    Optional<Producto> findByNombreIgnoreCase(String name);
    @Query(value = "SELECT p FROM Producto p")
    Page<Producto> findAll(Pageable pageable);

    Optional<Producto> findById(UUID id);

    //Filtrado por categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.nombre = :nombreCategoria")
    List<Producto> findByCategoriaNombre(@Param("nombreCategoria") String nombreCategoria);
    

    //Producto mas vendido 
        @Query("""
        SELECT lv.producto 
        FROM LineaDeVenta lv 
        GROUP BY lv.producto 
        ORDER BY SUM(lv.cantidad) DESC
        """)
    List<Producto> findProductosMasVendidos(Pageable pageable);


    //  Categoría que más productos ha vendido
@Query("""
    SELECT lv.producto.categoria.nombre, SUM(lv.cantidad) as totalVendido
    FROM LineaDeVenta lv
    GROUP BY lv.producto.categoria.nombre
    ORDER BY totalVendido DESC
    """)
List<Object[]> findCategoriasMasVendidas(Pageable pageable);

//  Producto más vendido por categoría
@Query("""
    SELECT lv.producto.categoria.nombre, lv.producto, SUM(lv.cantidad) as totalVendido
    FROM LineaDeVenta lv
    GROUP BY lv.producto.categoria.nombre, lv.producto
    HAVING SUM(lv.cantidad) = (
        SELECT MAX(suma)
        FROM (
            SELECT SUM(lv2.cantidad) as suma
            FROM LineaDeVenta lv2
            WHERE lv2.producto.categoria.nombre = lv.producto.categoria.nombre
            GROUP BY lv2.producto
        )
    )
    """)
List<Object[]> findProductoEstrellaPorCategoria();



}
