package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.repositorio.LineaDeVentaRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import com.salesianostriana.dam.farma_app.repositorio.VentaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepo ventaRepo;
    private final ProductoRepo productoRepo;


    public Venta edit(Venta venta) {
        return  ventaRepo.save(venta);
    }
    // Ver si existe un carrito
    public boolean hayProductosEnCarrito(Cliente c, GetProductoDto dto) {
        Optional<Venta> venta = ventaRepo.findByClienteAndFinalizadaFalse(c);
        if (venta.isPresent()) {
            return venta.get().getLineasVenta().stream()
                    .anyMatch(lv -> lv.getProducto().equals(dto));
        }
        return false;
    }
    //devolver el carrito
    public Venta obtenerCarrito(Cliente c) {
        return ventaRepo.findByCliente(c);
    }
    // Metodo agregar producto
    public void addProducto(Cliente c, GetProductoDto dto) {
        Venta carrito = ventaRepo.findByCliente(c);
        Optional<LineaDeVenta> lineaExistente = BuscarPorProducto(c, dto);
        //Traemos la venta
        if (lineaExistente.isPresent()) {
            // Si ya existe, incrementar la cantidad
            LineaDeVenta linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + 1);
        } else {
            // Si no existe, crear una nueva línea con cantidad 1
            carrito.addLineaPedido(LineaDeVenta.builder()
                    .producto(productoRepo.findById(dto.id()).orElse(null))
                    .cantidad(1) // Asegúrate de inicializar la cantidad
                    .build());
        }

        // Guardar los cambios
        ventaRepo.save(carrito); // Cambiado de ventaServicio.edit a ventaRepo.save
    }
    // Buscar por Producto
    private Optional<LineaDeVenta> BuscarPorProducto(Cliente c, GetProductoDto dto) {
        Venta carrito = getCarrito(c);
        return carrito.getLineasVenta().stream().filter(lv -> lv.getProducto().getId() == dto.id()).findFirst();
        //en la labda lo que hacemos es coger el id del producto que nos pasan y lo comparamos, y nos devolverá el perimero que encuentre
    }

    public Venta getCarrito(Cliente cliente) {
        return getVentasSinFinalizar(cliente).orElseGet(() -> crearCarrito(cliente));//nos devuelve la venta sin finalizar y si no me crea un carrito
    }

    public Optional<Venta> getVentasSinFinalizar(Cliente cliente) {
        return ventaRepo.findByClienteAndFinalizadaFalse(cliente);
    }
    public Venta crearCarrito(Cliente c) {

        Venta carrito = Venta.builder().cliente(c).estado(false).build();
        return carrito;

    }

    public void finalizarCompra(Cliente c) {
        Venta carrito = getCarrito(c);
        carrito.getLineasVenta().forEach(lineaVenta ->{ //esta lambda recorre los cursos en el carrito y setea su atributo bolleano comprado para que no nos aparezca despues de comprarlo
            Producto p  = lineaVenta.getProducto();

        });
        carrito.setEstado(true);
        carrito.setFechaCreacion(LocalDate.now().atStartOfDay());
        carrito.setImporteTotal(getImporteTotal(c));

      edit(carrito);
    }

    public double getImporteTotal(Cliente c) {
        Venta carrito = getCarrito(c);

        return carrito.getLineasVenta().stream()
                .mapToDouble(lv -> lv.getProducto().getPrecio() * lv.getCantidad())
                .sum();
    }

}
