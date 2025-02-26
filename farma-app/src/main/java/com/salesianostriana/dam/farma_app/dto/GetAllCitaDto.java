package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record GetAllCitaDto(
                               String tittle,


                               String title,

                               double precio,

                               boolean oferta,


                               LocalDateTime fecha_inicio,


                               LocalDateTime fecha_fin) {public static List<CreateCitaDto> of(List<Cita> citas) {
    return citas.stream()
            .map(CreateCitaDto::of)
            .collect(Collectors.toList());
}
}
