package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
import com.salesianostriana.dam.farma_app.error.ComentarioDuplicadoException;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.repositorio.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.ComentarioRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioService {


    private final ComentarioRepo comentarioRepositorio;
    private final ClienteRepo clienteRepositorio;
    private final ProductoRepo productoRepositorio;
    @Transactional
    public Comentario crearComentario(Cliente c, CreateComentarioDto dto) {
        // Buscar Cliente y Producto por nombre
        Cliente clienteVerdadero = clienteRepositorio.findFirstByUsername(c.getUsername())
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado"));

        Producto producto = productoRepositorio.findById(dto.productoId())
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado: " + dto.productoId()));

        // Crear el comentario
        ComentarioKey id = new ComentarioKey(clienteVerdadero.getId(), producto.getId());
        if (comentarioRepositorio.existsById(id)) {
            throw new ComentarioDuplicadoException("Ya has comentado este producto. ¿Tal vez quieras editarlo?");
        }


        Comentario comentario = Comentario.builder()
                .id(id)
                .cliente(clienteVerdadero)
                .producto(producto)
                .comentarios(dto.comentario())
                .build();

        // Guardar el comentario
        return comentarioRepositorio.save(comentario);
    }
}