package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name= "admin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Usuario{

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
