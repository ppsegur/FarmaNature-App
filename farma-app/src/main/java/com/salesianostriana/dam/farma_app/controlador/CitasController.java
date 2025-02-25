package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.CitaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cita")
@Tag(name = "citas", description = "El controlador para los distintas citas  ")
public class CitasController {

    //Borrar el controlador y realizarlo en los de cliente y farmaceutico
    private CitaService citaService;

    @PostMapping("/agendar")
    public ResponseEntity<String> agendarCita(
            @RequestParam UUID idFarmaceutico,
            @AuthenticationPrincipal Cliente c,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @RequestParam int duracion,
            @RequestParam double precio,
            @RequestParam boolean especial) {

            citaService.agendarCita(idFarmaceutico, c, fecha, duracion, precio, especial);
            return ResponseEntity.ok("Cita agendada correctamente.");

    }
}
