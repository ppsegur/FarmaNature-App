package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Producto> addProducto(@RequestBody @Valid EditProductDto nuevo){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.saveproducto(nuevo));

    }
}
