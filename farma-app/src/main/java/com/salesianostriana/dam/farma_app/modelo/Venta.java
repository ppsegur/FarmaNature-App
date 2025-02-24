package com.salesianostriana.dam.farma_app.modelo;

import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id")
    private Cliente cliente;


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


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Venta venta = (Venta) o;
        return getId() != null && Objects.equals(getId(), venta.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
