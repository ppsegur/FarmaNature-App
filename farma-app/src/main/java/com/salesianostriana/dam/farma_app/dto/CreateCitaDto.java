package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import jakarta.validation.constraints.*;


import java.time.LocalDateTime;

public record CreateCitaDto(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
        String titulo,

        @NotNull(message = "El farmacéutico no puede ser nulo")
        FarmaceuticoCitaDto usernameFarma,

        @NotNull
        ClienteCitaDto usernameCliente,


        @PositiveOrZero(message = "El precio no puede ser negativo")
        double precio,

        boolean oferta,

        LocalDateTime fechaInicio,


        @NotNull(message = "La fecha de fin no puede ser nula")
        @FutureOrPresent(message = "La fecha de fin debe ser en el presente o futuro")
        LocalDateTime fecha_fin


) {

    public static CreateCitaDto of(Cita c) {
        return new CreateCitaDto(
                c.getTitulo(),
                FarmaceuticoCitaDto.of(c.getFarmaceutico()),
                ClienteCitaDto.of(c.getCliente()),
                c.getPrecioCita(),
                c.isEspecial(),
                c.getCitasPk().getFechaInicio(),

                c.getFechaFin()
        );
    }



}
