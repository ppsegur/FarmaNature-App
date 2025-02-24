package com.salesianostriana.dam.farma_app.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.Date;

@Builder
public record EditProductDto(

        @NotNull(message = "El precio no puede ser nulo")
        String nombre,
        @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
     String descripcion,

        @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser un número positivo")
    Double precio,

        @NotNull(message = "El stock no puede ser nulo")
    @PositiveOrZero(message = "El stock debe ser un número positivo o cero")
    Integer stock,

        @PastOrPresent(message = "La fecha de publicación no puede ser futura")
    Date fechaPublicacion,

        String imagen,
        Boolean oferta,
        EditCategoriaDto categoria) {
}
