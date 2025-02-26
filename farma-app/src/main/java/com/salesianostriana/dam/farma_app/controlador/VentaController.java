package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.LineaVentaDto;
import com.salesianostriana.dam.farma_app.dto.VentaDto;
import com.salesianostriana.dam.farma_app.modelo.LineaDeVenta;
import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.servicio.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtener el carrito de compras del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/")
    public ResponseEntity<VentaDto> obtenerCarrito(@AuthenticationPrincipal Cliente cliente) {
      VentaDto v  = VentaDto.of(ventaService.getCarrito(cliente));
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(v);
    }
    @Operation(summary = "Agregar un producto al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al agregar el producto")
    })
    @PostMapping("/producto/")
    public ResponseEntity<GetProductoDto> agregarProductoAlCarrito(
            @AuthenticationPrincipal Cliente cliente,
            @RequestBody GetProductoDto dto) {
        ventaService.addProducto(cliente, dto );
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Eliminar un producto del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado en el carrito")
    })
    @DeleteMapping("/producto/{productoId}")
    public ResponseEntity<?> eliminarProductoDelCarrito(
            @AuthenticationPrincipal Cliente cliente,
            @PathVariable UUID productoId) {
        ventaService.eliminarProductoDelCarrito(cliente, productoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar la cantidad de un producto en el carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida")
    })
    @PutMapping("/producto/{productoId}/cantidad/{cantidad}")
    public ResponseEntity<GetProductoDto> actualizarCantidadProducto(
            @AuthenticationPrincipal Cliente cliente,
            @PathVariable UUID productoId,
            @PathVariable int cantidad) {
        ventaService.actualizarCantidad(cliente, productoId, cantidad);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Finalizar la compra del carrito actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra finalizada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se pudo finalizar la compra")
    })
    @PostMapping("/finalizar")
    public ResponseEntity<VentaDto> finalizarCompra(@AuthenticationPrincipal Cliente cliente) {
        VentaDto v  = ventaService.finalizarCompra(cliente);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(v);
    }
    @Operation(summary = "Obtener el historial de compras del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay historial de compras")
    })
    @GetMapping("/historial")
    public List<VentaDto> obtenerHistorialCompras(@AuthenticationPrincipal Cliente cliente) {
        List<Venta> historial = ventaService.obtenerHistorialCompras(cliente);
        List<VentaDto> historialDto = historial.stream()
                .map(VentaDto::of)
                .toList();

        return historialDto;
    }


}