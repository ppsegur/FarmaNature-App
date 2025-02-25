package com.salesianostriana.dam.farma_app.modelo.users;

import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.Venta;
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
@Table(name="cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "edad")
    private int edad;

    @OneToMany(mappedBy = "cliente")
    private Set<Comentario> comentarios = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Venta> ventas = new HashSet<>();

    public void removeComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setCliente(null);
    }



    @Override
    public String getRole() {
        return "CLIENTE";
    }

}
