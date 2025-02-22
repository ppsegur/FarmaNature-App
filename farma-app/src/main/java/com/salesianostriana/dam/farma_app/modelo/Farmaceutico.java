package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "farmaceutico")
public class Farmaceutico extends Usuario{

    private String direccionLocal;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Column(name = "turno")
    private Set<Turno> turno = new HashSet<>() ;

    @Override
    public String getRole() {
        return "FARMACEUTICO";
    }
}
