package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import lombok.Builder;

@Builder
public record GetCategoriaDto(
        String nombre
) {
    public static GetCategoriaDto of(ComentarioKey.Categoria c){
        return GetCategoriaDto.builder()
                .nombre(c.getNombre())
                .build();

    }
}
