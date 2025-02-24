package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.servicio.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comentario", description = "El controlador para los dsitintos comentarios  ")
@RequestMapping("/comentario")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @PostMapping("/")
    public ResponseEntity<Comentario> crearComentario(@RequestBody CreateComentarioDto dto,
                                                        @AuthenticationPrincipal Cliente c) {
        Comentario comentarioCreado = comentarioService.crearComentario(c,dto);
        return ResponseEntity.status(201).body(comentarioCreado);
    }

    // Editar un comentario
    @PutMapping("/editar")
    public ResponseEntity<Comentario> editarComentario(
            @AuthenticationPrincipal Cliente c,
            @RequestBody CreateComentarioDto dto) {
        Comentario comentarioEditado = comentarioService.editarComentario(c, dto);
        return ResponseEntity.ok(comentarioEditado);
    }
}
