package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;

import java.io.Serializable;
import java.util.UUID;


public class ComentarioKey implements Serializable {

    @Column(name = "cliente_id")
    UUID cliente_id;

    @Column(name = "producto_id")
    UUID producto_id;


}
