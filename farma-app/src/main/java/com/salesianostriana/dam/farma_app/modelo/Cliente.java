package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente extends Usuario{

    private String direccion;
    private String telefono;
    private int edad;


    @Override
    public String getRole() {
        return "CLIENTE";
    }

}
