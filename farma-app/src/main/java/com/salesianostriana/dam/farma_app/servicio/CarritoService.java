package com.salesianostriana.dam.farma_app.servicio;


import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.repositorio.VentaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoService {
    private final VentaRepo repo;

    public Optional<Venta> getVentasSinFinalizar(Cliente c) {
        return repo.findByClienteAndEstadoFalse(c);
    }
}
