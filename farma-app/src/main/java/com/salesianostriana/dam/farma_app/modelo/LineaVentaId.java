package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineaVentaId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Venta venta;
    private Long id;
}
