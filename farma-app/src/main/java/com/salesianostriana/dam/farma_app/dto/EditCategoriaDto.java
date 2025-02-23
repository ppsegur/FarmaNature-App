package com.salesianostriana.dam.farma_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EditCategoriaDto(
        @NotBlank(message = "El nombre de una categoria puede estar vacía")
        @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
        String nombre,

        List<GetProductoDto> productos
) {
}
