package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepo repo;
}
