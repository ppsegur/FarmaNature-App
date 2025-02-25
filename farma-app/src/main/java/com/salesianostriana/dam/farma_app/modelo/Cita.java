package com.salesianostriana.dam.farma_app.modelo;

import com.salesianostriana.dam.farma_app.modelo.users.Cliente;
import com.salesianostriana.dam.farma_app.modelo.users.Farmaceutico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cita {

    @EmbeddedId
    private CitaPk citasPk = new CitaPk();

    @ManyToOne
    @MapsId("id_farmaceutico")
    @JoinColumn(name = "id_farmaceutico")
    private Farmaceutico farmaceutico;

    @ManyToOne
    @MapsId("id_cliente")
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private int duracion;
    private double precioCita;
    private boolean especial;

    // Helper doctor
    public void addToFarmaceutico(Farmaceutico farmaceutico) {

        farmaceutico.getCitas().add(this);

        this.farmaceutico = farmaceutico;
    }

    public void removeFromDoctor(Farmaceutico farmaceutico) {

        farmaceutico.getCitas().remove(this);

        this.farmaceutico = null;
    }

    // Helper cliente
    public void addToCliente(Cliente cliente) {
        cliente.getCitas().add(this);

        this.cliente = cliente;
    }

    public void removeFromAlumno(Cliente cliente) {
        cliente.getCitas().remove(this);
        this.cliente = null;
    }
}