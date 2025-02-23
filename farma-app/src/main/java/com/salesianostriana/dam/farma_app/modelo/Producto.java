package com.salesianostriana.dam.farma_app.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="producto")
public class Producto {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "nombre")
        private String nombre;

        @Column(name = "descripcion")
        private String descripcion;
        @Column(name = "precio")
        private Double precio;
        @Column(name = "stock")
        private Integer stock;
        @Column(name = "fecha_publicacion")
        private Date fechaPublicacion;
        @Column(name = "imagen")
        private String imagen;
        @Column(name = "oferta")
        private Boolean oferta;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;


    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> reseñas = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Producto producto = (Producto) o;
        return getId() != null && Objects.equals(getId(), producto.getId())
                && getNombre() != null && Objects.equals(getNombre(), producto.getNombre());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(nombre);
    }
}
