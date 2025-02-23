package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepo repo;

    public Categoria saveCategoria(EditCategoriaDto nuevo){
        return repo.save(Categoria
                .builder().nombre(nuevo.nombre()).build());
    }
}
