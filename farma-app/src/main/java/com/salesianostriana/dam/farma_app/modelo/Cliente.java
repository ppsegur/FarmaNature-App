package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
