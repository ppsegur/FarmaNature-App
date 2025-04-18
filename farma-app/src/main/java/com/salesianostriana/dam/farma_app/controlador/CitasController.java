package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/citas")
@Tag(name = "citas", description = "El controlador para los distintas citas  ")
public class CitasController {

    private final CitaService citaService;

    @Operation(summary = "Crear una nueva cita", description = "Permite a un cliente registrar una nueva cita con un farmacéutico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cita creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la cita inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado para crear citas")
    })
    @PostAuthorize("hasRole('CLIENTE')")
    @PostMapping("/")
    public ResponseEntity<CreateCitaDto> crearCita(@RequestBody @Valid CreateCitaDto dto, @AuthenticationPrincipal Cliente cliente) {
        Cita cita = citaService.crearCita(dto, cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCitaDto.of(cita));
    }

    @GetMapping("/farmaceutico/{username}")
    @Operation(summary = "Obtener citas por farmacéutico", description = "Devuelve todas las citas asociadas a un farmacéutico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Farmacéutico no encontrado")
    })
    public Set<CreateCitaDto> getCitasByFarmaceutico(
            @PathVariable String username) {
        return citaService.listarCitasDelFarmaceutico(username).stream().map(CreateCitaDto::of).collect(Collectors.toSet());

    }

    @GetMapping("/cliente/{username}")
    @Operation(summary = "Obtener citas por cliente", description = "Devuelve todas las citas asociadas a un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public Set<CreateCitaDto> getCitasByCliente(
            @Parameter(description = "Username del cliente", required = true)
            @PathVariable String username) {
        return citaService.listarCitasByCliente(username).stream().map(CreateCitaDto::of).collect(Collectors.toSet());
    }

    @DeleteMapping("/{clienteId}/{farmaceuticoId}/{fechaInicio}")
    public ResponseEntity<?> eliminarCita(
            @PathVariable UUID clienteId,
            @PathVariable UUID farmaceuticoId,
            @PathVariable String fechaInicio) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime fechaInicioLDT = LocalDateTime.parse(fechaInicio, formatter);
            citaService.eliminarCita(clienteId, farmaceuticoId, fechaInicioLDT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si la eliminación fue exitosa
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la cita: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Obtener todas las citas",
            description = "Devuelve una lista de todas las citas disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de citas obtenida con éxito",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CreateCitaDto.class)))),
                    @ApiResponse(responseCode = "204", description = "No hay citas disponibles")
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<CreateCitaDto>> obtenerTodasLasCitas() {
        List<CreateCitaDto> citas = citaService.obtenerTodasLasCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
    }


