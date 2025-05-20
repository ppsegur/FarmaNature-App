
package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.CategoriaProductCount;
import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.GetCategoriaDto;
import com.salesianostriana.dam.farma_app.error.CategoriaNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepo repo;

    public Categoria saveCategoria(GetCategoriaDto nuevo){
        return repo.save(Categoria
                .builder().nombre(nuevo.nombre()).build());
    }

    public List<Categoria> findAll() {
        List<Categoria> categorias = repo.findAll();
        if(categorias.isEmpty()) {
            throw new CategoriaNotFoundException("No se encontraron empresas", HttpStatus.NOT_FOUND);
        }
        return categorias;
    }
    public void delete(String nombre) {
        Categoria categoria = repo.findByNombre(nombre);

        Categoria c = categoria;
        // c.getProductos().forEach(producto -> producto.removeFromCategorias(c));
        if(c == null) {
            throw new CategoriaNotFoundException(nombre , HttpStatus.NOT_FOUND);
        }
        repo.deleteById(c.getId());
    }


    public Categoria edit(EditCategoriaDto dto, String nombre) {
        return Optional.ofNullable(repo.findByNombre(nombre))
                .map(old -> {
                    old.setNombre(dto.nombre());
                    return repo.save(old);
                })
                .orElseThrow(() -> new CategoriaNotFoundException(
                        "Categoría con nombre " + nombre + " no encontrada",
                        HttpStatus.NOT_FOUND
                ));
    }


    public List<CategoriaProductCount> obtenerConteoProductosPorCategoria() {
        return repo.contarProductosPorCategoria();
    }

    @Transactional
    public CategoriaProductCount obtenerCategoriaConMasProductos() {
        return repo.topCategoriaPorProducto()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay categorías disponibles"));
    }


}
