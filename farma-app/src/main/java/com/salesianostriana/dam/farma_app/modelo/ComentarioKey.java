package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioKey implements Serializable {

    @Column(name = "cliente_id")
    private UUID clienteId;

    @Column(name = "producto_id")
    private UUID productoId;
}