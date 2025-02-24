package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.LineaVentaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LineaDeVentaRepo extends JpaRepository<LineaDeVenta, LineaVentaId> {
    boolean existsByProducto_productoId(UUID productoId);

}
