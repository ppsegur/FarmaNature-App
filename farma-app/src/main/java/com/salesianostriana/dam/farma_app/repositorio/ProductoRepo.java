package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductoRepo extends
        JpaRepository<Producto, UUID> , PagingAndSortingRepository<Producto, UUID>,
        JpaSpecificationExecutor<Producto> {
    Optional<Producto> findFirstByNombre(String name);
    @Query(value = "SELECT p FROM Producto p")
    Page<Producto> findAll(Pageable pageable);

    Optional<Producto> findById(UUID id);

}
