package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;


@Builder
public record GetCategoriaDto(
        UUID id,
        @NotBlank(message = "El titulito de la categoría no puede estar vacío mas alegría que solo contamos con el nombre")
        String nombre
) {
    @Builder
    public static GetCategoriaDto of(Categoria c){
        return new  GetCategoriaDto(
                c.getId(),
                c.getNombre()
               
        );
    }
        
    
}
