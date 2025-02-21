package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "admin")
public class Admin extends Usuario{

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
