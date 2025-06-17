package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.modelo.Venta;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface VentaRepo extends JpaRepository<Venta, UUID> {

    Optional<Venta> findByClienteAndEstadoFalse(Cliente cliente);

    Venta findByCliente(Cliente cliente);

   List<Venta> findByClienteAndEstadoTrue(Cliente cliente);
    Venta findVentaByClienteAndEstadoFalse(Cliente cliente);

@Query("SELECT DISTINCT v FROM Venta v LEFT JOIN FETCH v.lineasVenta WHERE v.cliente = :cliente AND v.estado = true")
List<Venta> findByClienteAndEstadoTrueWithLineas(@Param("cliente") Cliente cliente);
    @Query("SELECT EXTRACT(DAY FROM v.fechaCreacion), COUNT(v) " +
       "FROM Venta v WHERE v.estado = true AND EXTRACT(MONTH FROM v.fechaCreacion) = :mes AND EXTRACT(YEAR FROM v.fechaCreacion) = :anio " +
       "GROUP BY EXTRACT(DAY FROM v.fechaCreacion) ORDER BY EXTRACT(DAY FROM v.fechaCreacion)")
List<Object[]> ventasPorDiaDelMes(@Param("mes") int mes, @Param("anio") int anio);

@Query("SELECT DISTINCT v FROM Venta v LEFT JOIN FETCH v.lineasVenta")
List<Venta> findAllWithLineas();

    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.lineasVenta WHERE v.cliente = :cliente AND v.estado = false")
    Optional<Venta> findCarritoWithLineas(@Param("cliente") Cliente cliente);
}
