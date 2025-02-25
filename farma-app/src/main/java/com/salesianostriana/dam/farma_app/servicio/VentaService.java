package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import com.salesianostriana.dam.farma_app.repositorio.VentaRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
        Optional<Venta> venta = ventaRepo.findByClienteAndEstadoFalse(c);
        if (venta.isPresent()) {
            return venta.get().getLineasVenta().stream()
                    .anyMatch(lv -> lv.getProducto().equals(dto));
        }
        return false;
    }
    //devolver el carrito
    public void addProducto(Cliente c, GetProductoDto dto) {
        Producto producto = productoRepo.findById(dto.id())
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado", HttpStatus.NOT_FOUND));

        Venta carrito = getCarrito(c);

        Optional<LineaDeVenta> lineaExistente = BuscarPorProducto(c, producto);

        if (lineaExistente.isPresent()) {
            LineaDeVenta linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + 1);
        } else {
            carrito.addLineaPedido(LineaDeVenta.builder()
                    .producto(producto)
                    .cantidad(1)
                    .build());
        }

        ventaRepo.save(carrito);
    }

    // Buscar por Producto
    private Optional<LineaDeVenta> BuscarPorProducto(Cliente c, Producto p) {
        Venta carrito = getCarrito(c);
        return carrito.getLineasVenta().stream().filter(lv -> lv.getProducto().getId() == p.getId()).findFirst();
    }

    public Venta getCarrito(Cliente cliente) {
        return getVentasSinFinalizar(cliente).orElseGet(() -> crearCarrito(cliente));//nos devuelve la venta sin finalizar y si no me crea un carrito
    }

    public Optional<Venta> getVentasSinFinalizar(Cliente cliente) {
        return ventaRepo.findByClienteAndEstadoFalse(cliente);
    }
    public Venta crearCarrito(Cliente c) {

        Venta carrito = Venta.builder().cliente(c).estado(false).build();
        return carrito;

    }

    public void finalizarCompra(Cliente c) {
        Venta carrito = getCarrito(c);
        carrito.getLineasVenta().forEach(lineaVenta ->{ //esta lambda recorre los productos en el carrito y setea su atributo bolleano comprado para que no nos aparezca despues de comprarlo
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

    @Transactional
    public void eliminarProductoDelCarrito(Cliente c, UUID productoId) {
        Venta carrito = ventaRepo.findByCliente(c);
        Optional<LineaDeVenta> lineaExistente = carrito.getLineasVenta().stream()
                .filter(lv -> lv.getProducto().getId().equals(productoId))
                .findFirst();

        if (lineaExistente.isPresent()) {
            carrito.getLineasVenta().remove(lineaExistente.get());
            ventaRepo.save(carrito);
        } else {
            throw new ProductoNotFoundException("Producto no encontrado en el carrito", HttpStatus.NOT_FOUND);
        }
    }

    public void eliminarProducto(Cliente cliente, UUID productoDto) {
        Venta carrito = getCarrito(cliente);
        carrito.getLineasVenta().removeIf(lv -> lv.getProducto().getId().equals(productoDto));
        ventaRepo.save(carrito);
    }

    public void actualizarCantidad(Cliente cliente, UUID productoDto, int cantidad) {
        if (cantidad <= 0) {
            eliminarProducto(cliente, productoDto);
            return;
        }
        Producto p = productoRepo.findById(productoDto).orElse(null);


        Venta carrito = getCarrito(cliente);
        Optional<LineaDeVenta> lineaOpt = BuscarPorProducto(cliente, p);

        if (lineaOpt.isPresent()) {
            LineaDeVenta linea = lineaOpt.get();
            linea.setCantidad(cantidad);
            ventaRepo.save(carrito);
        }
    }

    public Venta obtenerHistorialCompras(Cliente cliente) {
        return ventaRepo.findByClienteAndEstadoTrue(cliente);
    }

}
