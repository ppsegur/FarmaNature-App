package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
    private Categoria categoria;



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
