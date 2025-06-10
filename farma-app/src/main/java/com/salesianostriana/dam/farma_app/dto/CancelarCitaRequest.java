package com.salesianostriana.dam.farma_app.dto;

import java.time.LocalDateTime;

public record  CancelarCitaRequest(
    String motivo,
    LocalDateTime fechaInicio
) {
    
}
