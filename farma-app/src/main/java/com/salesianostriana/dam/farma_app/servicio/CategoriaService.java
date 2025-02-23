package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepo repo;
}
