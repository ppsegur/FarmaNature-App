package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private String cliente;

    @OneToMany(
            mappedBy = "pedido",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<LineaDeVenta> lineasVenta = new ArrayList<>();

    // Helpers

    public void addLineaPedido(LineaDeVenta lineaDeVenta) {
        lineasVenta.add(lineaDeVenta);
        lineaDeVenta.setVenta(this);
    }

    public void removeLineaPedido(LineaDeVenta lineaDeVenta) {
        lineasVenta.remove(lineaDeVenta);
    }

}
