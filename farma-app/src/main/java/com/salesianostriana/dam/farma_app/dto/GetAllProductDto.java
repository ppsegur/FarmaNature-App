package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Producto;
import lombok.Builder;

import java.util.List;
public record GetAllProductDto(List<GetProductoDto> listaProductos
) {
    @Builder
    public GetAllProductDto(List<GetProductoDto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public static GetAllProductDto fromDto(List<Producto> listadoProductosSinProcesasr) {
        return GetAllProductDto.builder()
                .listaProductos(listadoProductosSinProcesasr.stream().map(producto -> GetProductoDto.of(producto, producto.getImagen())) // Usar una lambda
                        .toList())
                .build();
    }
}
