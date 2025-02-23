package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Producto;
import lombok.Builder;

import java.util.List;
@Builder
public record GetAllProductDto(List<GetProductoDto> listaProductos
) {

    public static GetAllProductDto fromDto(List<Producto> listadoProductosSinProcesasr) {
        return GetAllProductDto.builder()
                .listaProductos(listadoProductosSinProcesasr.stream().map(GetProductoDto::of).toList()).build();


    }
}
