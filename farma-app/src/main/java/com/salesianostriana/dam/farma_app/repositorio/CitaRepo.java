package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CitaRepo extends JpaRepository<Cita, CitaPk> {
    long countByFarmaceuticoAndFechaInicioBetween(Farmaceutico farmaceutico, LocalDateTime inicio, LocalDateTime fin);

}
