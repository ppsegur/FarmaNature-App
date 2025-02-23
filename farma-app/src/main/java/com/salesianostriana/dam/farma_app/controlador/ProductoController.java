package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "producto", description = "El controlador para los distintas productos  ")
public class ProductoController {
    private final ProductoService productoService;


    @Operation(summary = "Registra una nueva categoría ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria creado con éxito",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EditCategoriaDto.class),
                                    examples = @ExampleObject(value = """
                                            {
                                            
                                                "nombre": "tés",
                                                "precio":21,
                                                "descripcion": "tés básicos set 20 sabores",
                                                "stock":11,
                                                "categoria":??,
                                                "imagen":??
                                                ""
                                            
                                            }
                                            """)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para registrar la producto",
                    content = @Content
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/producto")
    public ResponseEntity<Producto> addProducto(@RequestBody @Valid EditProductDto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.saveproducto(nuevo));

    }

    @Operation(summary = "Obtiene todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los productos",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Producto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                             {
                                               {
                                                  "nombre" : "té",                     
                                                  "categoria": "Medicamentos",                                           \s
                                               }
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado productos"
            )
    })
    @GetMapping("/producto/all")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        Page<Producto> productos = productoService.findAllProductos(page, size, sort);
        List<String> nombres = productos.getContent().stream()
                .map(Producto::getNombre)
                .toList();
        return ResponseEntity.ok(nombres);
    }
    @Operation(summary = "Obtiene un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la producto",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el producto con el id (id proporcionado)",
                    content = @Content)
    })
    @GetMapping("/producto/{id}")
    public GetProductoDto findById(@PathVariable UUID id) {
        return GetProductoDto.of(productoService.findById(id));
    }

    @Operation(summary = "Elimina un producto buscándola por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Producto eliminada con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el producto con el id (id proporcionado)",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetProductoDto.class),
                            examples = @ExampleObject(value = """
                {
                    "id": "550e8400-e29b-41d4-a716-446655440000",
                    "nombre": "Paracetamol",
                    "precio": 5.99,
                    "descripcion": "Analgésico y antipirético",
                    "stock": 100,
                    "categoria": "Medicamentos",
                    "imagen": "paracetamol.jpg"
                }
            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para actualizar el producto",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el producto con el id proporcionado",
                    content = @Content
            )
    })
            @PostAuthorize("hasRole('ADMIN')")
            @PutMapping("/producto/{id}")
            public ResponseEntity<GetProductoDto> editProducto(
            @PathVariable UUID id,
            @RequestBody @Valid EditProductDto editProductDto) {
        Producto productoEditado = productoService.edit(editProductDto, id);
        GetProductoDto response = GetProductoDto.of(productoEditado);
        return ResponseEntity.ok(response);
    }
}
