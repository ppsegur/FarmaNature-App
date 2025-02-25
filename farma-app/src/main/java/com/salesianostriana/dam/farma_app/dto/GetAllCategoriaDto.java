package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllCategoriaDto(

        List<GetCategoriaDto> listado
) {
    public static GetAllCategoriaDto fromDto(List<ComentarioKey.Categoria> listadoT){
        return GetAllCategoriaDto.builder()
                .listado(listadoT.stream().map(GetCategoriaDto::of).toList())
                .build();

    }
}
