package com.salesianostriana.dam.farma_app.dto;


import java.util.UUID;

public record CreateComentarioDto(
        String username,
        UUID productoId,
        String comentario
) {
}
