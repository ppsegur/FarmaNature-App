package com.salesianostriana.dam.farma_app.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CitaPk implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Column(name = "cliente_id")
    private UUID id_cliente;


    @Column(name = "farmaceutico_id")
    private UUID id_farmaceutico;
    @Column(insertable=false, updatable=false, name = "fecha_inicio")
    private LocalDateTime fechaInicio;
}
