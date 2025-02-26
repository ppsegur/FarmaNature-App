package com.salesianostriana.dam.farma_app.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateComentarioDto(

        UUID productoId,
        @NotBlank(message = "no puede estar en blanco")
        @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
        String comentario
) {
}
