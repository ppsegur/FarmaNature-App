package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.Turno;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.repositorio.CitaRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.FarmaceuticoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
    @RequiredArgsConstructor
    public class CitaService {


    private final CitaRepo citaRepo;
    private final FarmaceuticoRepo farmaceuticoRepo;
    private final ClienteRepo clienteRepo;

    @Transactional
    public Cita crearCita(CreateCitaDto dto, Cliente c) {
        Farmaceutico farmaceuticoVerdadero = farmaceuticoRepo.findFirstByUsername(dto.usernameFarma().username())
                .orElseThrow(() -> new UsuarioNotFoundException("Farmacéutico no encontrado", HttpStatus.NOT_FOUND));

        LocalDateTime fechaInicio = LocalDateTime.from(dto.fecha_inicio());

        long citasEnElDia = citaRepo.countByFarmaceuticoAndFechaInicioBetween(
                farmaceuticoVerdadero, fechaInicio.toLocalDate().atStartOfDay(), fechaInicio.toLocalDate().atTime(23, 59));
        if (citasEnElDia >= 5) {
            throw new IllegalStateException("El farmacéutico ya tiene 5 citas en este día");
        }

        Turno turno = determinarTurno(fechaInicio);

        double precio = switch (dto.title().toLowerCase()) {
            case "basica" -> 20;
            case "general" -> 30;
            case "terapia" -> 50;
            default -> throw new IllegalArgumentException("Tipo de cita no válido");
        };
        if (dto.oferta()) {
            precio /= 2;
        }

        CitaPk citaPk = new CitaPk(c.getId(), farmaceuticoVerdadero.getId(), fechaInicio);

        Cita cita = new Cita(
                citaPk,
                farmaceuticoVerdadero,
                c,
                dto.title(),
                dto.fecha_inicio(),
                dto.fecha_fin(),
                precio,
                dto.oferta()
        );

        return citaRepo.save(cita);
    }


        private Turno determinarTurno(LocalDateTime fecha) {
            int hora = fecha.getHour();
            if (hora >= 6 && hora < 12) return Turno.MANANA;
            if (hora >= 12 && hora < 18) return Turno.TARDE;
            return Turno.NOCHE;
        }
    }



