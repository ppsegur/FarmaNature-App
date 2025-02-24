package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.error.ComentarioDuplicadoException;
import com.salesianostriana.dam.farma_app.error.ComentarioNotFoundException;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.error.UsuarioNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import com.salesianostriana.dam.farma_app.modelo.ComentarioKey;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.repositorio.users.ClienteRepo;
import com.salesianostriana.dam.farma_app.repositorio.ComentarioRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComentarioService {


    private final ComentarioRepo comentarioRepositorio;
    private final ClienteRepo clienteRepositorio;
    private final ProductoRepo productoRepositorio;

    @Transactional
    public Comentario crearComentario(Cliente c, CreateComentarioDto dto) {

        Cliente clienteVerdadero = clienteRepositorio.findFirstByUsername(c.getUsername())
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));

        Producto producto = productoRepositorio.findById(dto.productoId())
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado: " + dto.productoId(), HttpStatus.NOT_FOUND));

        ComentarioKey id = new ComentarioKey(clienteVerdadero.getId(), producto.getId());
        if (comentarioRepositorio.existsById(id)) {
            throw new ComentarioDuplicadoException("Ya has comentado este producto. ¿Tal vez quieras editarlo?", HttpStatus.MULTI_STATUS);
        }


        Comentario comentario = Comentario.builder()
                .id(id)
                .cliente(clienteVerdadero)
                .producto(producto)
                .comentarios(dto.comentario())
                .build();

        return comentarioRepositorio.save(comentario);
    }

    // Editar un comentario
    @Transactional
    public Comentario editarComentario(Cliente c, CreateComentarioDto dto) {
        Producto p = productoRepositorio.findById(dto.productoId())
                .orElseThrow();
        ComentarioKey id = new ComentarioKey(c.getId(), p.getId());
        Comentario comentario = comentarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        comentario.setComentarios(dto.comentario());
        return comentarioRepositorio.save(comentario);
    }

    @Transactional
    public Set<Comentario> listarComentariosDeCliente(String username) {

        Cliente c = clienteRepositorio.findFirstByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado", HttpStatus.NOT_FOUND));


        Set<Comentario> comentarios = c.getComentarios();
        if (comentarios.isEmpty()) {
            throw new ComentarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return comentarios;
    }

    @Transactional
    public Set<Comentario> listarComentariosDeProducto(String username) {
        Producto p = productoRepositorio.findByNombreIgnoreCase(username)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado", HttpStatus.NOT_FOUND));

        Set<Comentario> comentarios = p.getReseñas();
        if (comentarios.isEmpty()) {
            throw new ComentarioNotFoundException("comentarios noexisten", HttpStatus.NOT_FOUND);
        }
        return comentarios;
    }

    @Transactional
    public void eliminarComentario(Cliente cliente, UUID productoId) {

    ComentarioKey id = new ComentarioKey(cliente.getId(), productoId);

    Comentario comentario = comentarioRepositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));



    comentarioRepositorio.delete(comentario);
}
    }















