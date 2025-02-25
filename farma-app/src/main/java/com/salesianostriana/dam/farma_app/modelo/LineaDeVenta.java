package com.salesianostriana.dam.farma_app.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(LineaVentaId.class)
public class LineaDeVenta {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    @ManyToOne
    private Venta venta;



    @ManyToOne
    @JoinColumn(name = "producto_id",
            foreignKey = @ForeignKey(name = "fk_producto_lv"))
    @JsonBackReference
    private Producto producto;

    private int cantidad;

    private double precioVenta;

    public double getPrecioVenta() {
        return producto.getPrecio();
    }

    //Metodos helper paraa añadir un Producto
    public void addProducto(Producto producto) {
        this.producto = producto;
        this.cantidad++;
        producto.addLineaVenta(this);
    }
    public void removeProducto(Producto producto) {
        this.setProducto(producto);
        this.cantidad--;
        if (this.cantidad == 0) {
       producto.removeLineaVenta(this);
   }

    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LineaDeVenta that = (LineaDeVenta) o;
        return getId() != null && Objects.equals(getId(), that.getId())
                && getVenta() != null && Objects.equals(getVenta(), that.getVenta());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, venta);
    }
}
