package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCitaDto(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
        String tittle,

        @NotNull(message = "El farmacéutico no puede ser nulo")
        FarmaceuticoCitaDto usernameFarma,



        @PositiveOrZero(message = "El precio no puede ser negativo")
        double precio,

        boolean oferta,

        @NotNull(message = "La fecha de inicio no puede ser nula")
        @FutureOrPresent(message = "La fecha de inicio debe ser en el presente o futuro")
        LocalDateTime fecha_inicio,

        @NotNull(message = "La fecha de fin no puede ser nula")
        @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro")
        LocalDateTime fecha_fin
) {

    public static CreateCitaDto of(Cita c) {
        return CreateCitaDto.builder()
                .tittle(c.getTitulo())
                .usernameFarma(FarmaceuticoCitaDto.of(c.getFarmaceutico()))
                .precio(c.getPrecioCita())
                .oferta(c.isEspecial())
                .fecha_inicio(c.getFechaInicio())
                .fecha_fin(c.getFecha_fin())
                .build();
    }
}
