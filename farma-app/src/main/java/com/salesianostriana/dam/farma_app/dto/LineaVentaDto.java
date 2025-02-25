package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import lombok.Builder;

@Builder
public record LineaVentaDto (
     Long productoId,
     String nombreProducto,
    double precioUnitario,
    int cantidad,
     double subtotal
){
    public static LineaVentaDto of(LineaDeVenta lineaVenta) {
        return LineaVentaDto.of(LineaDeVenta.builder()
                .venta(lineaVenta.getVenta())
                .precioVenta(lineaVenta.getPrecioVenta())
                .cantidad(lineaVenta.getCantidad())
                .producto(lineaVenta.getProducto())
                .build());

    }
}