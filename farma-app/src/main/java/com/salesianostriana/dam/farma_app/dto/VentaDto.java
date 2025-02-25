package com.salesianostriana.dam.farma_app.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record VentaDto (
     UUID id,
     LocalDateTime fechaCreacion,
     boolean estado,
     double importeTotal,
        //usar esto
     LineaVentaDto lineasVenta
    ){
}
