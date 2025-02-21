package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "farmaceutico")
public class Farmaceutico extends Usuario{

    private String direccionLocal;
    private String turno;

    @Override
    public String getRole() {
        return "FARMACEUTICO";
    }
}
