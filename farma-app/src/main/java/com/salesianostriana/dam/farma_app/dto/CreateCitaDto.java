package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record CreateCitaDto(
        String tittle,
        ClienteCitaDto username,
        FarmaceuticoCitaDto usernameFarma,
        String title,
        double precio,
        boolean oferta,
        LocalDateTime fecha_inicio,
        LocalDateTime fecha_fin
) {

    public static CreateCitaDto of(Cita c) {
        return CreateCitaDto.builder()
                .tittle(c.getTitulo())
                .username(ClienteCitaDto.of(c.getCliente()))
                .usernameFarma(FarmaceuticoCitaDto.of(c.getFarmaceutico()))
                .title(c.getTitulo())
                .precio(c.getPrecioCita())
                .oferta(c.isEspecial())
                .fecha_inicio(c.getFecha_inicio().toLocalDate().atStartOfDay())
                .fecha_fin(LocalDateTime.from(c.getFecha_fin().toLocalTime()))
                .build();
    }
}
