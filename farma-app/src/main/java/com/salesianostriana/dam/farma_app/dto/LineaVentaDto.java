package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import lombok.Builder;

import java.util.UUID;

@Builder
public record LineaVentaDto (
     UUID productoId,
     String nombreProducto,
    double precioUnitario,
    int cantidad,
     double subtotal
){
    public static LineaVentaDto of(LineaDeVenta lineaVenta) {
        return LineaVentaDto.builder()
                .productoId(lineaVenta.getProducto().getId())
                .nombreProducto(lineaVenta.getProducto().getNombre())
                .precioUnitario(lineaVenta.getPrecioVenta())
                .cantidad(lineaVenta.getCantidad())
                .subtotal(lineaVenta.getPrecioVenta() * lineaVenta.getCantidad())
                .build();
    }
}