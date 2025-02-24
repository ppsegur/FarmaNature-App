package com.salesianostriana.dam.farma_app.dto;


import java.util.UUID;

public record CreateComentarioDto(
        UUID clienteId,
        UUID productoId,
        String comentario
) {
}
