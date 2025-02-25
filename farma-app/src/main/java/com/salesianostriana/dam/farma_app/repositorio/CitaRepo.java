package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepo extends JpaRepository<Cita, CitaPk> {
}
