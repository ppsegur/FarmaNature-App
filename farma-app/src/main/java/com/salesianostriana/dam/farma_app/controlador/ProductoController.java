package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.EditProductDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(productos.getContent());
    }
}
