package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sound.sampled.Line;

public record VentaDto (
     UUID id,
     LocalDateTime fechaCreacion,
     boolean estado,
     double importeTotal,
        //usar esto
     List<LineaVentaDto> lineasVenta
    ){
    public static VentaDto of(Venta venta) {
        List<LineaVentaDto> lineas = venta.getLineasVenta() == null ? List.of() : venta.getLineasVenta().stream()
                .map(LineaVentaDto::of)
                .toList();
        System.out.println("Venta " + venta.getId() + " tiene " + lineas.size() + " lineas de venta");
        return new VentaDto(
                venta.getId(),
                venta.getFechaCreacion(),
                venta.isEstado(),
                venta.getImporteTotal(),
                lineas
        );
    }
}