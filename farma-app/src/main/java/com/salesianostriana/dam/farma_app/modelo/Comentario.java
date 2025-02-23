package com.salesianostriana.dam.farma_app.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Comentario {
    @EmbeddedId
    ComentarioKey id;

    @ManyToOne
    @MapsId("clienteId")
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    Cliente cliente;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    Producto producto;

    String  comentarios;
}
