package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/citas")
@Tag(name = "citas", description = "El controlador para los distintas citas  ")
public class CitasController {

    private final CitaService citaService;

    @PostMapping("/")
    public ResponseEntity<CreateCitaDto> crearCita(@RequestBody CreateCitaDto dto, @AuthenticationPrincipal Cliente cliente ) {
        Cita cita = citaService.crearCita(dto, cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCitaDto.of(cita));
    }

    @GetMapping("/farmaceutico/{username}")
    @Operation(summary = "Obtener citas por farmacéutico", description = "Devuelve todas las citas asociadas a un farmacéutico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Farmacéutico no encontrado")
    })
    public List<CreateCitaDto> getCitasByFarmaceutico(
            @PathVariable String username) {
        List<CreateCitaDto> citas = citaService.getCitasByFarmaceutico(username);
        return ResponseEntity.ok(citas).getBody();
    }

    @GetMapping("/cliente/{username}")
    @Operation(summary = "Obtener citas por cliente", description = "Devuelve todas las citas asociadas a un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Citas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN','FARMACEUTICO') ")
    public List<CreateCitaDto> getCitasByCliente(
            @Parameter(description = "Username del cliente", required = true)
            @PathVariable String username) {
        List<CreateCitaDto> citas = citaService.getCitasByCliente(username);
        return ResponseEntity.ok(citas).getBody();

    }
}
