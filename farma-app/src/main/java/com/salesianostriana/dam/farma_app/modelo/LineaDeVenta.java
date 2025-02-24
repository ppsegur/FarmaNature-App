package com.salesianostriana.dam.farma_app.modelo;

import jakarta.persistence.*;
import lombok.*;

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
   
}
