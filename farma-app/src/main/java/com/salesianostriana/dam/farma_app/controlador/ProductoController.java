package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.query.SearchCriteria;
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
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.UUID;

@Log
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
    @PreAuthorize("hasAnyRole('ADMIN','FARMACEUTICO')")
    @PostMapping("/producto")
    public ResponseEntity<GetProductoDto> addProducto(@RequestPart("file") MultipartFile file,
            @RequestPart("producto") @Valid GetProductoDto nuevo) {
        Producto  producto =  productoService.saveproducto(nuevo, file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(producto.toString())
                .toUriString();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetProductoDto.of(producto,uri));

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
                                                  "nombre" : "té",                    \s
                                                  "categoria": "Medicamentos",                                           \s
                                               }
                                             }
                                           \s"""
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
    public GetProductoDto findById(@PathVariable UUID id ) {
        Producto producto = productoService.findById(id);
        String imagenUrl = (producto.getImagen());
        return GetProductoDto.of(producto, imagenUrl);
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
    @PreAuthorize("hasAnyRole('ADMIN','FARMACEUTICO')")
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
                                            "nombre": "Paracetamol",
                                            "descripcion": "Analgésico y antipirético",
                                            "precio": 5.99,
                                            "stock": 160,
                                            "fechaPublicacion": "2023-04-12",
                                            "imagen": "paracetamol.jpg",
                                            "oferta": false,
                                            "categoria": {
                                                "nombre": "Medicamentos"
                                            }
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
            @PostAuthorize("hasAnyRole('ADMIN','FARMACEUTICO')")
            @PutMapping("/producto/{id}")
            public ResponseEntity<GetProductoDto> editProducto(
            @PathVariable UUID id,
            @RequestBody @Valid EditProductDto editProductDto) {
        Producto productoEditado = productoService.edit(editProductDto, id);
        String imagenUrl = productoEditado.getImagen();
        //CAmbios para las imágenes
        GetProductoDto response = GetProductoDto.of(productoEditado, imagenUrl);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/buscar/")
    public ResponseEntity<List<GetProductoDto>> buscar(@RequestParam(value = "search", required = false) String search) {
        log.info(search);
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+)");
            Matcher matcher = pattern.matcher(search);
            while (matcher.find()) {
                String key = matcher.group(1);
                String operation = matcher.group(2);
                String value = matcher.group(3);

                log.info("Key: " + key);
                log.info("Operation: " + operation);
                log.info("Value: " + value);

                params.add(new SearchCriteria(key, operation, value));
            }
        }

        List<GetProductoDto> productos = productoService.search(params);
        return ResponseEntity.ok(productos);
    }


    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene una lista de productos filtrados por el nombre de la categoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductoDto.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron productos para la categoría especificada",
                    content = @Content)
    })
    @GetMapping("/productoCategoria/")
    public ResponseEntity<List<GetProductoDto>> filtrarPorCategoria(
            @RequestParam(value = "categoria", required = false) String categoria) {
        List<GetProductoDto> productos = productoService.filtrarPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }
}
