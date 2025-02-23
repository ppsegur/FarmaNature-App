package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.EditCategoriaDto;
import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepo repo;

    public Producto saveproducto(EditProductDto nuevo){
        return repo.save(Producto
                .builder().nombre(nuevo.nombre())
                .descripcion(nuevo.descripcion())
                        .stock(nuevo.stock())
                        .imagen(nuevo.imagen())
                        .fechaPublicacion(nuevo.fechaPublicacion())
                        .oferta(nuevo.oferta())
                        .categoria(nuevo.categoria())
                .build());
    }

}
