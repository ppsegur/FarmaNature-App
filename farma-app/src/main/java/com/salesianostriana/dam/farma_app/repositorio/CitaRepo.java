package com.salesianostriana.dam.farma_app.repositorio;

import com.salesianostriana.dam.farma_app.dto.CreateCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.ClienteCitaDto;
import com.salesianostriana.dam.farma_app.dto.user.FarmaceuticoCitaDto;
import com.salesianostriana.dam.farma_app.modelo.Cita;
import com.salesianostriana.dam.farma_app.modelo.CitaPk;
import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CitaRepo extends JpaRepository<Cita, CitaPk> {

    // F¡unciones utilizadas para crear las citas
long countByFarmaceuticoAndCitasPk_FechaInicio(
    Farmaceutico farmaceutico,
    LocalDateTime fecha_inicio
    
);
long countByClienteAndCitasPk_FechaInicio(
    Cliente cliente,
    LocalDateTime fecha_inicio
 
);    /////////////////////////////
    List<Cita> findByFarmaceuticoUsername(String  username);
    // Consulta para obtener citas por cliente
    List<Cita> findByClienteUsername(String username);

    Cita findByTitulo(String titulo);

    @Query("SELECT c.farmaceutico.username FROM Cita c GROUP BY c.farmaceutico.username ORDER BY COUNT(c) DESC LIMIT 1")
    String findTopFarmaceuticoUsername();

    @Query("SELECT c.cliente.username FROM Cita c GROUP BY c.cliente.username ORDER BY COUNT(c) DESC LIMIT 1")
    String findTopClienteUsername();

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.farmaceutico.username = :username")
    int countCitasByFarmaceuticoUsername(@Param("username") String username);

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.cliente.username = :username")
    int countCitasByClienteUsername(@Param("username") String username);

    //Contar citas por farmaceutico
    long countByFarmaceutico_Username(String username);

    // Contar citas por cliente
    long countByCliente_Username(String username);


    @Query(value = "SELECT COUNT(*) * 1.0 / COUNT(DISTINCT DATE(fecha_inicio)) FROM cita", nativeQuery = true)
    double mediaCitasPorDia();
    @Query(value = "SELECT COUNT(*) * 1.0 / COUNT(DISTINCT EXTRACT(MONTH FROM fecha_inicio)) FROM cita", nativeQuery = true)
    double obtenerMediaCitasPorMes();



    // Consulta para ver si es una cita futura 
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cita c WHERE c.citasPk = :citasPk AND c.citasPk.fechaInicio > CURRENT_TIMESTAMP")
    boolean esCitaFutura(@Param("citasPk") CitaPk citasPk);


    // Consulta para eliminar citas por farmaceutico
    void deleteByFarmaceuticoId(UUID idFarmaceutico);
    // Consulta para eliminar citas por cliente
    void deleteByClienteId(UUID idCliente);


}

