package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.Date;

@Builder
public record GetProductoDto(
                             String descripcion,

                             Double precio,

                             Integer stock,

                             Date fechaPublicacion,

                             String imagen,
                             Boolean oferta,
        EditCategoriaDto categoria
        ) {

    public static GetProductoDto of(Producto p){
        return GetProductoDto.builder()
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .fechaPublicacion(p.getFechaPublicacion())
                .imagen(p.getImagen())
                .oferta(p.getOferta())
                .build();

    }
}
