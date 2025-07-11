package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CategoriaProductCount;
import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.GetCategoriaDto;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.servicio.CategoriaService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "El controlador para los distintas categorias  ")
public class CategoriaController {

    private  final CategoriaService service;


    @Operation(summary = "Registra una nueva categoría ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria creado con éxito",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Categoria.class),
                                    examples = @ExampleObject(value = """
                                {
                                 
                                    "nombre": "tés",
                                
                                }
                                """)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para registrar la categoria",
                    content = @Content
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN','FARMACEUTICO')")
    @PostMapping("/categoria")
    public ResponseEntity<Categoria> addCategoria(@RequestBody @Valid  GetCategoriaDto getCategoriaDto){
        Categoria categoria = service.saveCategoria(getCategoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoria);

    }
    @Operation(summary = "Obtiene todas las categorias y las devuelve en forma de listado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las categorias",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Categoria.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                              "nombre":"tés"
                                                        }
                                                    ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron las categorias"
            )
    })
//No le daremos autentificación por si podemos usarlo para que el usuario liste estas y desddde ellas elija después los distintos productos
    @PreAuthorize("permitAll()")
    @GetMapping("/categoria/all")
    public List<GetCategoriaDto> findAll() {
        return service.findAll().stream().map(GetCategoriaDto::of).toList();
    }

    @Operation(summary = "Elimina una categoría buscándola por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Categoria  eliminada con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró la categoría con el nombre (nombre proporcionado)",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMACEUTICO')")
    @DeleteMapping("/categoria/{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        service.delete(nombre);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita una categoría  existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Categoría actualizada con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation =Categoria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró la categoría con el id (id proporcionado)",
                    content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'FARMACEUTICO')")
    @PutMapping("/categoria/{nombre}")
    public GetCategoriaDto edit(@PathVariable String nombre, @RequestBody @Valid EditCategoriaDto categoriaDto) {
        return GetCategoriaDto.of(service.edit(categoriaDto, nombre));
    }


    @GetMapping("/conteo-productos")
    public List<CategoriaProductCount> contarProductosPorCategoria() {
        return service.obtenerConteoProductosPorCategoria();
    }

    @GetMapping("/categoria/top-categoria")
    public CategoriaProductCount obtenerCategoriaConMasProductos() {
        return service.obtenerCategoriaConMasProductos();



    }

}
