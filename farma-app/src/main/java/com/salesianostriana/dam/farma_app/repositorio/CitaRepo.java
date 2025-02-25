package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepo extends JpaRepository<Cita, CitaPk> {
    long countByFarmaceuticoAndFechaInicioBetween(Farmaceutico farmaceutico, LocalDateTime start, LocalDateTime end);
    List<Cita> findByFarmaceuticoUsername(String  username);

    // Consulta para obtener citas por cliente
    List<Cita> findByClienteUsername(String username);
}

