package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
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
    @DeleteMapping("/eliminar/{clienteId}/{id}")
    public ResponseEntity<?> eliminarComentario(
            @PathVariable UUID clienteId,
            @PathVariable UUID id) {
        comentarioService.eliminarComentario(clienteId, id);
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
public ResponseEntity<?> getAllComentarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort) {
    Page<Comentario> comentarios = comentarioService.findAllComentarios(page, size, sort);
    List<GetComentarioDto> resultado = comentarios.getContent().stream()
            .map(GetComentarioDto::of)
            .toList();
    return ResponseEntity.ok(resultado);
}


        //EndPoint que devuelve el producto con mas cmentarios 
        @Operation(summary = "Obtener el producto con más comentarios")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Producto con más comentarios encontrado"),
                @ApiResponse(responseCode = "404", description = "No se encontraron comentarios")
        })
        @GetMapping("/producto-con-mas-comentarios")
        public ResponseEntity<Producto> productoConMasComentarios() {
            Producto producto = comentarioService.productoConMasComentarios();
            if (producto == null) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.ok(producto);
        }

        @Operation(summary = "Obtener el cliente que más comentarios ha hecho")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Cliente con más comentarios encontrado"),
                @ApiResponse(responseCode = "404", description = "No se encontraron comentarios")
        })
        @GetMapping("/cliente-que-mas-comenta")
        public ResponseEntity<Cliente> usuarioQueMasComenta() {
            Cliente cliente = comentarioService.usuarioQueMasComenta();
            if (cliente == null) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.ok(cliente);
        }


        @GetMapping("/top3-productos-mas-comentados")
                public ResponseEntity<?> getTop3ProductosConMasComentarios() {
                List<Object[]> top3 = comentarioService.top3ProductosConMasComentarios();
                return ResponseEntity.ok(top3);
                
        }

        
        @GetMapping("/media-comentarios-mes")
        public ResponseEntity<?> getMediaComentariosPorMes() {
                List<Object[]> media = comentarioService.mediaComentariosPorMes();                        return ResponseEntity.ok(media);
        }
}
