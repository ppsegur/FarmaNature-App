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
@PrimaryKeyJoinColumn(name = "id")
public class Farmaceutico extends Usuario{

    @Column(name = "direccion")
    private String direccion;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "farmaceutico_turno",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Builder.Default
    @Column(name = "turno")
    private Set<Turno> turno = new HashSet<>() ;

    @Override
    public String getRole() {
        return "FARMACEUTICO";
    }
}
