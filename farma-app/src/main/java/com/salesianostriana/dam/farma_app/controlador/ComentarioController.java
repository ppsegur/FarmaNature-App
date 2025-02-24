package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.servicio.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/")
    public ResponseEntity<Comentario> crearComentario(@RequestBody @Valid CreateComentarioDto dto,
                                                        @AuthenticationPrincipal Cliente c) {
        Comentario comentarioCreado = comentarioService.crearComentario(c,dto);
        return ResponseEntity.status(201).body(comentarioCreado);
    }

    // Editar un comentario
    @PutMapping("/editar")
    public ResponseEntity<Comentario> editarComentario(
            @AuthenticationPrincipal Cliente c,
            @RequestBody @Valid  CreateComentarioDto dto) {
        Comentario comentarioEditado = comentarioService.editarComentario(c, dto);
        return ResponseEntity.ok(comentarioEditado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarComentario(
            @AuthenticationPrincipal Cliente c,
            @PathVariable UUID id) {
        comentarioService.eliminarComentario(c, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{username}")
    public Set<GetComentarioDto> listarComentariosDeCliente(
            @PathVariable String username) {

        return comentarioService.listarComentariosDeCliente(username)
                .stream().map(GetComentarioDto::of)
                .collect(Collectors.toSet());

    }
    @GetMapping("/producto/{username}")
    public Set<GetComentarioDto> listarComentariosDeproducto(
            @PathVariable String username) {

        return comentarioService.listarComentariosDeProducto(username)
                .stream().map(GetComentarioDto::of)
                .collect(Collectors.toSet());

    }

}
