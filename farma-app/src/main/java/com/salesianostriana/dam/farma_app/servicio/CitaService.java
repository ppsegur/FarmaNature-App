package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.Turno;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.repositorio.CitaRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.FarmaceuticoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitaService {
    private final CitaRepo citaRepo;
    private final FarmaceuticoRepo farmaceuticoRepo;
    private final ClienteRepo clienteRepo;

    public Cita agendarCita(UUID idFarmaceutico, Cliente c , LocalDateTime fecha, int duracion, double precio, boolean especial) {
        Optional<Farmaceutico> farmaceuticoOpt = farmaceuticoRepo.findById(idFarmaceutico);

        if (farmaceuticoOpt.isPresent()) {
            Farmaceutico farmaceutico = farmaceuticoOpt.get();

            // Verificar si el farmacéutico trabaja en el turno correspondiente a la fecha
            Turno turnoCita = determinarTurno(fecha);
            if (!farmaceutico.getTurno().contains(turnoCita)) {
                throw new IllegalArgumentException("El farmacéutico no trabaja en ese turno.");
            }

            CitaPk citaPk = new CitaPk(idFarmaceutico, c.getId(), fecha);
            Cita nuevaCita = new Cita(citaPk, farmaceutico, null, duracion, precio, especial);
            return citaRepo.save(nuevaCita);
        }
        throw new IllegalArgumentException("Farmacéutico no encontrado.");
    }

    private Turno determinarTurno(LocalDateTime fecha) {
        int hora = fecha.getHour();
        if (hora >= 6 && hora < 14) return Turno.MANANA;
        if (hora >= 14 && hora < 22) return Turno.TARDE;
        return Turno.NOCHE;
    }
}
