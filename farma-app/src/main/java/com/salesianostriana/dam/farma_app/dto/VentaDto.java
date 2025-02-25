package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record VentaDto (
     UUID id,
     LocalDateTime fechaCreacion,
     boolean estado,
     double importeTotal,
        //usar esto
     List<LineaVentaDto> lineasVenta
    ){
    public static VentaDto of(Venta venta) {
        return VentaDto.builder()
                .id(venta.getId())
                .fechaCreacion(venta.getFechaCreacion())
                .estado(venta.isEstado())
                .importeTotal(venta.getLineasVenta().stream()
                        .mapToDouble(lv -> lv.getProducto().getPrecio() * lv.getCantidad())
                        .sum())
                .lineasVenta(venta.getLineasVenta().stream()
                        .map(LineaVentaDto::of).toList()
                )
                .build();
    }

}
