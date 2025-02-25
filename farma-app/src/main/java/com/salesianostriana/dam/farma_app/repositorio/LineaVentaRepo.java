package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LineaVentaRepo extends JpaRepository<LineaDeVenta, UUID> {
}
