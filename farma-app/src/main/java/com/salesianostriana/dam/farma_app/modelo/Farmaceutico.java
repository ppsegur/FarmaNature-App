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
@Table(name = "farmaceutico")
public class Farmaceutico extends Usuario{

    private String direccionLocal;
    private String turno;

    @Override
    public String getRole() {
        return "FARMACEUTICO";
    }
}
