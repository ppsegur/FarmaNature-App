package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;

import lombok.Builder;

import java.util.UUID;


public record LineaVentaDto (
     UUID productoId,
     String nombreProducto,
    double precioUnitario,
    int cantidad,
     double subtotal
){
    public static LineaVentaDto of(LineaDeVenta lineaVenta) {
        return new LineaVentaDto(
                lineaVenta.getProducto().getId(),
                lineaVenta.getProducto().getNombre(),
                lineaVenta.getPrecioVenta(),
                lineaVenta.getCantidad(),
                lineaVenta.getPrecioVenta() * lineaVenta.getCantidad()
        );
    }
}