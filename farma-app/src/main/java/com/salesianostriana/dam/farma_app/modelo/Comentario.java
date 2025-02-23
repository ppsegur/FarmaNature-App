package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;

@Entity
public class Comentario {
    @EmbeddedId
    ComentarioKey id;

    @ManyToOne
    @MapsId("clienteId")
    @JoinColumn(name = "cliente_id")
    Cliente cliente;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    Producto producto;

    String  comentarios;
}
