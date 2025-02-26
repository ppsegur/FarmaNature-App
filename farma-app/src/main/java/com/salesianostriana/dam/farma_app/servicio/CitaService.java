package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.error.ComentarioNotFoundException;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.*;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.repositorio.CitaRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.FarmaceuticoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

        double precio = switch (dto.tittle().toLowerCase()) {
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
                dto.tittle(),
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

    //Obtiene todas las citas de un farmacéutico específico

    @Transactional(readOnly = true)
    public List<CreateCitaDto> getCitasByFarmaceutico(String username) {
        farmaceuticoRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Farmacéutico no encontrado", HttpStatus.NOT_FOUND));

        List<Cita> citas = citaRepo.findByFarmaceuticoUsername(username);
        return Collections.singletonList(CreateCitaDto.of((Cita) citas));
    }

    //Obtiene todas las citas de un cliente específico

    @Transactional(readOnly = true)
    public List<CreateCitaDto> getCitasByCliente(String userename) {
        clienteRepo.findFirstByUsername(userename)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));

        List<Cita> citas = citaRepo.findByClienteUsername(userename);
        return Collections.singletonList(CreateCitaDto.of((Cita) citas));
    }

    @Transactional
    public Set<Cita> listarCitasDelFarmaceutico(String username) {

        Farmaceutico f  = farmaceuticoRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Farmaceutico no encontrado", HttpStatus.NOT_FOUND));


        Set<Cita> citas = f.getCitas();
        if (citas.isEmpty()) {
            throw new UsuarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return citas;
    }

    @Transactional
    public Set<Cita> listarCitasByCliente(String username) {
        Cliente c  = clienteRepo.findFirstByUsername(username)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado", HttpStatus.NOT_FOUND));

        Set<Cita> citas = c.getCitas();
        if (citas.isEmpty()) {
            throw new UsuarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return citas;
    }
    }



