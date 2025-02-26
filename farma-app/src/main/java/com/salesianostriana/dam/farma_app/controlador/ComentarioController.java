package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.servicio.ComentarioService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comentario", description = "El controlador para los dsitintos comentarios  ")
@RequestMapping("/comentario")
public class ComentarioController {
    private final ComentarioService comentarioService;



    @Operation(summary = "Crear un comentario", description = "Permite a un cliente autenticado crear un comentario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    })
    @PostMapping("/")
    public ResponseEntity<Comentario> crearComentario(@RequestBody @Valid CreateComentarioDto dto,
                                                        @AuthenticationPrincipal Cliente c) {
        Comentario comentarioCreado = comentarioService.crearComentario(c,dto);
        return ResponseEntity.status(201).body(comentarioCreado);
    }

    @Operation(summary = "Editar un comentario", description = "Permite a un cliente autenticado editar su comentario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario editado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado para editar este comentario"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    })
    @PutMapping("/editar")
    public ResponseEntity<Comentario> editarComentario(
            @AuthenticationPrincipal Cliente c,
            @RequestBody @Valid  CreateComentarioDto dto) {
        Comentario comentarioEditado = comentarioService.editarComentario(c, dto);
        return ResponseEntity.ok(comentarioEditado);
    }

    @Operation(summary = "Eliminar un comentario", description = "Permite a un cliente autenticado eliminar su comentario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentario eliminado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado para eliminar este comentario"),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarComentario(
            @AuthenticationPrincipal Cliente c,
            @PathVariable UUID id) {
        comentarioService.eliminarComentario(c, id);
        return ResponseEntity.noContent().build();
    }




    @Operation(summary = "Listar comentarios de un cliente", description = "Obtiene todos los comentarios realizados por un cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/cliente/{username}")
    public Set<GetComentarioDto> listarComentariosDeCliente(
            @PathVariable String username) {

        return comentarioService.listarComentariosDeCliente(username)
                .stream().map(GetComentarioDto::of)
                .collect(Collectors.toSet());

    }


    @Operation(summary = "Listar comentarios de un producto", description = "Obtiene todos los comentarios asociados a un producto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/producto/{username}")
    public Set<GetComentarioDto> listarComentariosDeproducto(
            @PathVariable String username) {

        return comentarioService.listarComentariosDeProducto(username)
                .stream().map(GetComentarioDto::of)
                .collect(Collectors.toSet());

    }

    @Operation(summary = "Obtiene todos los comentarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los comentarios",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comentario.class))

                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado productos"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        Page<Comentario> productos = comentarioService.findAllComentarios(page, size, sort);
        List<String> nombres = productos.getContent().stream()
                .map(Comentario::getComentarios)
                .toList();
        return ResponseEntity.ok(nombres);
    }

}
