package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.VentaDto;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.error.VentaNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.repositorio.LineaVentaRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import com.salesianostriana.dam.farma_app.repositorio.VentaRepo;
import com.salesianostriana.dam.farma_app.servicio.users.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepo ventaRepo;
    private final ProductoRepo productoRepo;
    private final CarritoService service;
    private final LineaVentaRepo lineaRepo;


    @Transactional
    public void edit(Venta venta) {
        ventaRepo.save(venta);
    }
    //Deprecado
    public boolean hayProductosEnCarrito(Cliente c, GetProductoDto dto) {
        Optional<Venta> venta = ventaRepo.findByClienteAndEstadoFalse(c);
        if (venta.isPresent()) {
            return venta.get().getLineasVenta().stream()
                    .anyMatch(lv -> lv.getProducto().equals(dto));
        }
        return false;
    }

    @Transactional
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
    @Transactional
    public Optional<LineaDeVenta> BuscarPorProducto(Cliente c, Producto p) {
        Venta carrito = getCarrito(c);
        return carrito.getLineasVenta().stream().filter(lv -> lv.getProducto().getId() == p.getId()).findFirst();
    }


    @Transactional
    public Venta getCarrito(Cliente cliente) {
        return ventaRepo.findByClienteAndEstadoFalse(cliente)
                .orElseGet(() -> {
                    Venta carrito = Venta.builder()
                            .cliente(cliente)
                            .estado(false)
                            .build();
                    ventaRepo.save(carrito); // Guarda el carrito en la base de datos
                    return carrito;
                });
    }



    @Transactional
    public VentaDto finalizarCompra(Cliente c) {

        Venta carrito = ventaRepo.findVentaByClienteAndEstadoFalse(c);



            // Buscar solo la compra en proceso

            if (carrito == null) {
                throw new VentaNotFoundException("No hay carrito en proceso", HttpStatus.NOT_FOUND);
            }

            // Calcular el total de la compra
            double total = carrito.getLineasVenta().stream()
                    .mapToDouble(lineaVenta -> lineaVenta.getProducto().getPrecio() * lineaVenta.getCantidad())
                    .sum();

            carrito.setEstado(true);
            carrito.setFechaCreacion(LocalDateTime.now());
            carrito.setImporteTotal(total);


            carrito = ventaRepo.save(carrito);


        return VentaDto.of(carrito);
    }


   /* @Transactional
    public double getImporteTotal(Cliente c) {
        Venta carrito = getCarrito(c);

        return carrito.getLineasVenta().stream()
                .mapToDouble(lv -> lv.getProducto().getPrecio() * lv.getCantidad())
                .sum();
    }
*/
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

      //EL actualizar no funciona
        if (cantidad <= 0) {
            eliminarProducto(cliente, productoDto);
            return;
        }
        Optional<Producto> p = productoRepo.findById(productoDto);
        if(p.isPresent()) {

        Optional<LineaDeVenta> lineaOpt = BuscarPorProducto(cliente, p.get());

        if (lineaOpt.isPresent()) {
            LineaDeVenta linea = lineaOpt.get();
            linea.setCantidad(cantidad);
            lineaRepo.save(linea);

        }}
    }

    public Venta obtenerHistorialCompras(Cliente cliente) {
        return ventaRepo.findByClienteAndEstadoTrue(cliente);
    }

}
