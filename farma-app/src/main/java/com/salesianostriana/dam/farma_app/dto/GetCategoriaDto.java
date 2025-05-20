package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record GetCategoriaDto(
        @NotBlank(message = "El titulito de la categoría no puede estar vacío mas alegría que solo contamos con el nombre")
        String nombre,
        UUID id,
        Set<GetProductoDto> producto
) {
    public static GetCategoriaDto of(Categoria c){

        return GetCategoriaDto.builder()

                .nombre(c.getNombre())
                .id(c.getId())


                .build();

    }
}
