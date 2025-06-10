package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import com.salesianostriana.dam.farma_app.servicio.CitaService;
import com.salesianostriana.dam.farma_app.servicio.users.UsuarioService;
import com.salesianostriana.dam.farma_app.util.MailService;

import io.swagger.v3.oas.annotations.Operation;
import java.time.format.DateTimeFormatter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/citas")
@Tag(name = "citas", description = "El controlador para los distintas citas  ")
public class CitasController {

    private final CitaService citaService;
    private final UsuarioService usuarioService;

    @PostAuthorize("hasRole('CLIENTE')")
    @PostMapping("/")
    public ResponseEntity<CreateCitaDto> crearCita(@RequestBody @Valid CreateCitaDto dto, @AuthenticationPrincipal Cliente cliente) {
        Cita cita = citaService.crearCita(dto, cliente);
        Farmaceutico f = cita.getFarmaceutico();

        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCitaDto.of(cita));
    }
    @PostAuthorize("hasAnyRole('ADMIN' ,'FARMACEUTICO')")
    @PostMapping("/crear")
    public ResponseEntity<CreateCitaDto> crearCitaFarmacutico(@RequestBody @Valid CreateCitaDto dto) {
        Cita cita = citaService.crearCitaFarma(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCitaDto.of(cita));
    }

    @GetMapping("/farmaceutico/{username}")
    public Set<CreateCitaDto> getCitasByFarmaceutico(@PathVariable String username) {
        return citaService.listarCitasDelFarmaceutico(username)
                .stream()
                .map(cita -> CreateCitaDto.of(cita)) // uso explícito de lambda en vez de method reference
                .collect(Collectors.toSet());
    }

    @GetMapping("/cliente/{username}")
    public Set<CreateCitaDto> getCitasByCliente(@PathVariable String username) {
        return citaService.listarCitasByCliente(username)
                .stream()
                .map(cita -> CreateCitaDto.of(cita)) 
                .collect(Collectors.toSet());
    }

    @GetMapping("/all")
    public List<CreateCitaDto> findAll() {
        return citaService.findAll()
                .stream()
                .map(cita -> CreateCitaDto.of(cita)) 
                .toList();
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarCitaPorIds(
            @RequestParam UUID clienteId,
            @RequestParam UUID farmaceuticoId,
            @RequestParam String fechaInicio,
            @RequestBody CreateCitaDto dto
    ) {
        try {
            citaService.editarCitaPorIds(clienteId, farmaceuticoId, fechaInicio, dto);
            return ResponseEntity.ok().body("Cita actualizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{titulo}")
    public ResponseEntity<?> eliminarCita(@PathVariable String titulo) {
        citaService.eliminarCita(titulo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top-farmaceutico")
    public ResponseEntity<FarmaceuticoCitaDto> getFarmaceuticoTop() {
        return ResponseEntity.ok(citaService.getFarmaceuticoConMasCitas());
    }

    @GetMapping("/top-cliente")
    public ResponseEntity<ClienteCitaDto> getClienteTop() {
        return ResponseEntity.ok(citaService.getClienteConMasCitas());
    }


    // Número de citas de un farmaceutico
    @GetMapping("/numero-citas-farmaceutico/{username}")
    public long getNumeroCitasFarmaceutico(@PathVariable String username) {
        return citaService.getNumeroCitasFarmaceutico(username);
    }

    // Número de citas de un cliente
    @GetMapping("/numero-citas-cliente/{username}")
    public long getNumeroCitasCliente(@PathVariable String username) {
        return citaService.getNumeroCitasCliente(username);
    }
    //Nº de citas por día
    @GetMapping("/media/dia")
    public ResponseEntity<Double> mediaCitasPorDia() {
        return ResponseEntity.ok(citaService.getMediaCitasPorDia());
    }
    //Nº de citas por mes
    @GetMapping("/media/mes")
    public ResponseEntity<Double> mediaCitasPorMes() {
        return ResponseEntity.ok(citaService.getMediaCitasPorMes());
    }



    


}