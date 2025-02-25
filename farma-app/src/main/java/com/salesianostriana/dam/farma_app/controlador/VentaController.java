package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.LineaVentaDto;
import com.salesianostriana.dam.farma_app.dto.VentaDto;
import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/")
    public ResponseEntity<VentaDto> obtenerCarrito(@AuthenticationPrincipal Cliente cliente) {
      VentaDto v  = VentaDto.of(ventaService.getCarrito(cliente));
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(v);
    }

    @PostMapping("/producto/")
    public ResponseEntity<GetProductoDto> agregarProductoAlCarrito(
            @AuthenticationPrincipal Cliente cliente,
            @RequestBody GetProductoDto dto) {
        ventaService.addProducto(cliente, dto );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/producto/{productoId}")
    public ResponseEntity<?> eliminarProductoDelCarrito(
            @AuthenticationPrincipal Cliente cliente,
            @PathVariable UUID productoId) {
        ventaService.eliminarProductoDelCarrito(cliente, productoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/producto/{productoId}/cantidad/{cantidad}")
    public ResponseEntity<GetProductoDto> actualizarCantidadProducto(
            @AuthenticationPrincipal Cliente cliente,
            @PathVariable UUID productoId,
            @PathVariable int cantidad) {
        ventaService.actualizarCantidad(cliente, productoId, cantidad);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizar")
    public ResponseEntity<VentaDto> finalizarCompra(@AuthenticationPrincipal Cliente cliente) {
        ventaService.finalizarCompra(cliente);
        VentaDto v  = ventaService.finalizarCompra(cliente);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(v);
    }



}