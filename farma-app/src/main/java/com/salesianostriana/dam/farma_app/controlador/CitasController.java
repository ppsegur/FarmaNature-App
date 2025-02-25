package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.CitaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cita")
@Tag(name = "citas", description = "El controlador para los distintas citas  ")
public class CitasController {

    private CitaService citaService;

    @PostMapping("/")
    public ResponseEntity<CreateCitaDto> crearCita(@RequestBody CreateCitaDto dto, @AuthenticationPrincipal Cliente cliente ) {
        Cita cita = citaService.crearCita(dto, cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCitaDto.of(cita));
    }

}
