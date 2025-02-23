package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.dto.user.PageResponse;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepo repo;
    private final CategoriaRepo categoriaRepo;

    @Transactional
    public Producto saveproducto(EditProductDto nuevo) {

        // Buscar la categoría por nombre
        Categoria categoria = categoriaRepo.findByNombre(nuevo.categoria().nombre());

        // Verificar si la categoría es nula
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría '" + nuevo.categoria().nombre() + "' no existe.");
        }

        // Construir y guardar el producto
        Producto producto = Producto.builder()
                .nombre(nuevo.nombre())
                .descripcion(nuevo.descripcion())
                .precio(nuevo.precio())
                .stock(nuevo.stock())
                .imagen(nuevo.imagen())
                .fechaPublicacion(nuevo.fechaPublicacion())
                .oferta(nuevo.oferta())
                .categoria(categoria)
                .build();

        categoria.addProducto(producto);
        return repo.save(producto);
    }
/*
    public Page<Producto> findAllProductos(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }
*/
public Page<Producto> findAllProductos(int page, int size, String[] sort) {
    String sortField = sort[0];
    Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
    return repo.findAll(pageable);
}



}
