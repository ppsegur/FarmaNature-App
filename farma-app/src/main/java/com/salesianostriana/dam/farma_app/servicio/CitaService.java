package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.error.CategoriaNotFoundException;
import com.salesianostriana.dam.farma_app.error.EntidadNotFoundException;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.*;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.repositorio.CitaRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.FarmaceuticoRepo;
import com.salesianostriana.dam.farma_app.repositorio.users.UsuarioRepo;
import com.salesianostriana.dam.farma_app.servicio.users.UsuarioService;
import com.salesianostriana.dam.farma_app.util.MailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CitaService {


    private final CitaRepo citaRepo;
    private final FarmaceuticoRepo farmaceuticoRepo;
    private final ClienteRepo clienteRepo;
    private final UsuarioRepo usuarioRepo;
    private final UsuarioService userService;
    private final MailService mailService;

    @Transactional
    public Cita crearCita(CreateCitaDto dto, Cliente c) {
        Farmaceutico farmaceuticoVerdadero = farmaceuticoRepo.findFirstByUsername(dto.usernameFarma().username())
                .orElseThrow(() -> new UsuarioNotFoundException("Farmacéutico no encontrado", HttpStatus.NOT_FOUND));

        LocalDateTime fechaInicio = LocalDateTime.now().plusMinutes(15);

        long citasEnElDia = citaRepo.countByFarmaceuticoAndCitasPk_FechaInicio(
                farmaceuticoVerdadero, fechaInicio.toLocalDate().atStartOfDay());
        if (citasEnElDia >= 5) {
            throw new IllegalStateException("El farmacéutico ya tiene 5 citas en este día");
        }

        Turno turno = determinarTurno(fechaInicio);

        double precio = switch (dto.titulo().toLowerCase()) {
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
                dto.titulo(),
                dto.fecha_fin(),
                precio,
                dto.oferta()
              
        );

        return citaRepo.save(cita);
    }
    //Método para crear cita como farmaceutico o admin
    @Transactional
    public Cita crearCitaFarma(CreateCitaDto dto) {
        Cliente cliente = clienteRepo.findFirstByUsername(dto.usernameCliente().username())
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));

        Farmaceutico farmaceutico = farmaceuticoRepo.findFirstByUsername(dto.usernameFarma().username())
                .orElseThrow(() -> new UsuarioNotFoundException("Farmacéutico no encontrado", HttpStatus.NOT_FOUND));

        LocalDateTime fechaInicio = LocalDateTime.now().plusMinutes(15);

        // Validación de citas
        long citasEnElDia = citaRepo.countByClienteAndCitasPk_FechaInicio(
                cliente, fechaInicio.toLocalDate().atStartOfDay());
        if (citasEnElDia >= 5) {
            throw new IllegalStateException("Límite de citas alcanzado");
        }

        // Cálculo de precio
        double precio = switch (dto.titulo().toLowerCase()) {
            case "basica" -> 20;
            case "general" -> 30;
            case "terapia" -> 50;
            default -> throw new IllegalArgumentException("Tipo de cita no válido");
        };

        if (dto.oferta()) {
            precio /= 2;
        }

        // Creación de la cita
        CitaPk citaPk = new CitaPk(cliente.getId(), farmaceutico.getId(), LocalDateTime.now().plusMinutes(15));

        Cita cita = Cita.builder()
                .citasPk(citaPk)
                .farmaceutico(farmaceutico)
                .cliente(cliente)
                .titulo(dto.titulo())
                .fechaFin(dto.fecha_fin())
                .precioCita(dto.precio())
                .especial(dto.oferta())
                .build();

        return citaRepo.save(cita);

    }

    private Turno determinarTurno(LocalDateTime fecha) {
        int hora = fecha.getHour();
        if (hora >= 6 && hora < 12) return Turno.MANANA;
        if (hora >= 12 && hora < 18) return Turno.TARDE;
        return Turno.NOCHE;
    }

    //Obtiene todas las citas de un farmacéutico específico
/*
    public List<CreateCitaDto> getCitasByFarmaceutico(String username) {
        farmaceuticoRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Farmacéutico no encontrado", HttpStatus.NOT_FOUND));

        List<Cita> citas = citaRepo.findByFarmaceuticoUsername(username);
        return citas.stream().map(CreateCitaDto::of).toList();
    }

    public List<CreateCitaDto> getCitasByCliente(String username) {
        clienteRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));

        List<Cita> citas = citaRepo.findByClienteUsername(username);
        return citas.stream().map(CreateCitaDto::of).toList();
    }
*/
    @Transactional
    public Set<Cita> listarCitasDelFarmaceutico(String username) {

        Farmaceutico f = farmaceuticoRepo.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Farmaceutico no encontrado", HttpStatus.NOT_FOUND));


        Set<Cita> citas = f.getCitas();
        if (citas.isEmpty()) {
            throw new UsuarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return citas;
    }

    @Transactional
    public Set<Cita> listarCitasByCliente(String username) {
        Cliente c = clienteRepo.findFirstByUsername(username)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado", HttpStatus.NOT_FOUND));

        Set<Cita> citas = c.getCitas();
        if (citas.isEmpty()) {
            throw new UsuarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return citas;
    }

    //Función para listar todas las citas
    @Transactional
    public List<Cita> findAll() {
        List<Cita> citass = citaRepo.findAll();
        if (citass.isEmpty()) {
            throw new CategoriaNotFoundException("No se encontraron empresas", HttpStatus.NOT_FOUND);
        }
        return citass;
    }

  @Transactional
public Cita editarCitaPorIds(UUID clienteId, UUID farmaceuticoId, String fechaInicioStr, CreateCitaDto dto) {
    LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr);
    System.out.println("Buscando cita con: clienteId=" + clienteId + ", farmaceuticoId=" + farmaceuticoId + ", fechaInicio=" + fechaInicio);
    CitaPk pk = new CitaPk(clienteId, farmaceuticoId, fechaInicio);
    Cita c = citaRepo.findById(pk)
            .orElseThrow(() -> new EntidadNotFoundException("No se encontró la cita con esos datos", HttpStatus.NOT_FOUND));
    c.setPrecioCita(dto.precio());
    c.setFechaFin(dto.fecha_fin());
    c.setTitulo(dto.titulo());
    c.setEspecial(dto.oferta());
    return citaRepo.save(c);
}

    //eliminar una cita
    @Transactional
    public void eliminarCita(String titulo) {
        Cita c = citaRepo.findByTitulo(titulo);
        citaRepo.delete(c);

    }
    //findFarmaceuticoConMasCitas (repositorio)
    // Funcion para  encontrar  nombre del farmaceutico con mas citas
    public FarmaceuticoCitaDto getFarmaceuticoConMasCitas() {
        String username = citaRepo.findTopFarmaceuticoUsername();
        Farmaceutico farmaceutico = (Farmaceutico) userService.findByUsername(username).orElseThrow();
        int total = citaRepo.countCitasByFarmaceuticoUsername(username);
        return FarmaceuticoCitaDto.of(farmaceutico);
    }

    public ClienteCitaDto getClienteConMasCitas() {
        String username = citaRepo.findTopClienteUsername();
        Cliente cliente = (Cliente) userService.findByUsername(username).orElseThrow();
        int total = citaRepo.countCitasByClienteUsername(username);
        return ClienteCitaDto.of(cliente);
    }

    public long getNumeroCitasFarmaceutico(String username) {
        return citaRepo.countByFarmaceutico_Username(username);
    }

    public long getNumeroCitasCliente(String username) {
        return citaRepo.countByCliente_Username(username);
    }

    //Función para sacar la media de citas por dia
    public double getMediaCitasPorDia() {
        return citaRepo.mediaCitasPorDia();
    }
    //Función para sacar la media de citas por mes
    public double getMediaCitasPorMes() {
        return citaRepo.obtenerMediaCitasPorMes();
    }

   
}






