package com.salesianostriana.dam.farma_app.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name="comentario")
public class Comentario {

    @EmbeddedId
    private ComentarioKey id;

    @ManyToOne
    @MapsId("clienteId")
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Producto producto;

    private String  comentarios;
}
