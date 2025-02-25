package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface VentaRepo extends JpaRepository<Venta, UUID> {

    Optional<Venta> findByClienteAndEstadoFalse(Cliente cliente);

    Venta findByCliente(Cliente cliente);

   Venta findByClienteAndEstadoTrue(Cliente cliente);
    Venta findVentaByClienteAndEstadoFalse(Cliente cliente);

}
