package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import lombok.Builder;

import java.util.Date;

@Builder
public record GetProductoDto(
        String descripcion,

        Double precio,

        Integer stock,

        Date fechaPublicacion,

        Boolean oferta,
        EditCategoriaDto categoria,
        String imagenUrl
        ) {

    public static GetProductoDto of(Producto p, String url){
        return new GetProductoDto(p.getDescripcion(),p.getPrecio(),p.getStock(),p.getFechaPublicacion() ,p.getOferta(),p.getCategoria(),url);
    }
}
