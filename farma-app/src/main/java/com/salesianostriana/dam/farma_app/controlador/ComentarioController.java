package com.salesianostriana.dam.farma_app.controlador;

import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.servicio.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comentario", description = "El controlador para los dsitintos comentarios  ")
@RequestMapping("/comentario")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @PostMapping("/")
    public ResponseEntity<Comentario> crearComentario(@RequestBody CreateComentarioDto dto) {
        Comentario comentarioCreado = comentarioService.crearComentario(dto);
        return ResponseEntity.status(201).body(comentarioCreado);
    }
}
