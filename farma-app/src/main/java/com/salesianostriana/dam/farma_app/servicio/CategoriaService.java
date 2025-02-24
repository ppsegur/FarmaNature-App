package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.error.CategoriaNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
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

    public Categoria saveCategoria(EditCategoriaDto nuevo){
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



        if(categoria == null) {
            throw new CategoriaNotFoundException("No se ha encontrado " , HttpStatus.NOT_FOUND);
        }
        repo.deleteById(categoria.getId());
    }


    public Categoria edit(EditCategoriaDto dto, String  nombre) {
        Categoria categoria = repo.findByNombre(nombre);
        if (dto.nombre() != null) {
            categoria.setNombre(dto.nombre());
        }

        return repo.save(categoria);
    }

}
