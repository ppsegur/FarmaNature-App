package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.dto.CreateComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetComentarioDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
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

@Service
@RequiredArgsConstructor
public class ComentarioService {


    private final ComentarioRepo comentarioRepositorio;
    private final ClienteRepo clienteRepositorio;
    private final ProductoRepo productoRepositorio;

    @Transactional
    public Comentario crearComentario(CreateComentarioDto dto) {
        // Buscar el cliente y el producto
        Cliente cliente = clienteRepositorio.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Producto producto = productoRepositorio.findById(dto.productoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear la clave primaria compuesta
        ComentarioKey id = new ComentarioKey(dto.clienteId(), dto.productoId());

        // Crear el comentario
        Comentario comentario = Comentario.builder()
                .id(id)
                .cliente(cliente)
                .producto(producto)
                .comentarios(dto.comentario())
                .build();

        // Guardar el comentario
      return  comentarioRepositorio.save(comentario);


    }
}
